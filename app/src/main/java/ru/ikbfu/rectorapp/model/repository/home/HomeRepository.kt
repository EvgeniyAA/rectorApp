package ru.ikbfu.rectorapp.model.repository.home

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.ikbfu.rectorapp.model.data.db.dao.FavoritesDao
import ru.ikbfu.rectorapp.model.data.db.dao.SelectionCommitteeDao
import ru.ikbfu.rectorapp.model.data.mapper.SelectionCommitteeElementMapper
import ru.ikbfu.rectorapp.model.data.server.ApiIKBFU
import ru.ikbfu.rectorapp.model.data.server.model.SelectionCommitteeElement
import ru.ikbfu.rectorapp.model.data.server.model.ElementType
import ru.ikbfu.rectorapp.model.data.server.model.UnderlineType
import ru.ikbfu.rectorapp.model.data.server.model.User
import ru.ikbfu.rectorapp.model.data.storage.CommonPrefs
import ru.ikbfu.rectorapp.model.system.SchedulersProvider
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val prefs: CommonPrefs,
    private val api: ApiIKBFU,
    private val selectionCommitteeDao: SelectionCommitteeDao,
    private val mapper: SelectionCommitteeElementMapper,
    private val schedulers: SchedulersProvider
) {
    private var selectionElements = arrayListOf<SelectionCommitteeElement>()
    private var idsToHide = arrayListOf<String>()

    val user
        get() = prefs.user

    val authorized
        get() = prefs.authorized

    fun collapse(value: Boolean) {
        prefs.isProfileCollapsed = value
    }

    fun logout(): Completable = Completable.fromAction {
        prefs.clear()
    }

    fun getMainData() =
        selectionCommitteeDao.getAll()
            .flattenAsFlowable { it }
            .map { mapper.toSelectionCommitteeElement(it) }
            .toList()
            .doOnSuccess { selectionElements = ArrayList(it) }
            .ignoreElement()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())

    fun filterAndCollapse(): Single<List<SelectionCommitteeElement>> = Single.fromCallable {
            val items = selectionElements.filter { it.isCollapsed }
            items.map { item ->
                val element = selectionElements.first { item.id == it.id }
                val index = selectionElements.indexOfFirst { item.id == it.id }
                selectionElements[index] = element.copy()
                selectionElements.map {
                    if (it.elementType == ElementType.PARENT_DATA || it.elementType == ElementType.AFTER_PARENT_DATA)
                        it.needToShow = true
                    it
                }
            }
            selectionElements.filter { it.needToShow }
    }

    fun getAllPriemApplications() = api.getCurrentPriemCount()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())

    fun updatePriem() = api.getPriemka()
        .doOnSuccess { selectionElements = ArrayList(it) }
        .flattenAsFlowable { it }
        .map { mapper.toSelectionCommitteeEntity(it) }
        .toList()
        .doOnSuccess {
            selectionCommitteeDao.deleteAll()
            selectionCommitteeDao.insertAll(it)  }
        .ignoreElement()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())

    fun collapseChildsOf(item: SelectionCommitteeElement): Single<List<SelectionCommitteeElement>> {
        collapsing(item)
        return Single.just(selectionElements.filter { it.needToShow })
    }

    private fun collapsing(item: SelectionCommitteeElement) {
        val element = selectionElements.first { item.id == it.id }
        val index = selectionElements.indexOfFirst { item.id == it.id }
        selectionElements[index] = element.copy().apply {
            isCollapsed = !element.isCollapsed
            needToShow = element.needToShow
            isFavorite = element.isFavorite
        }
        findAllIds(item)
        selectionElements.forEach {
            if (idsToHide.contains(it.id) && item.id != it.id) {
                it.needToShow = !it.needToShow
            }
        }
        idsToHide.clear()
    }


    fun findAllIds(item: SelectionCommitteeElement) {
        idsToHide.add(item.id)
        if (!item.childIds.isNullOrEmpty())
            selectionElements.filter { item.childIds.contains(it.id) }.forEach {
                if (it.isCollapsed) {
                    idsToHide.add(it.id)
                    return@forEach
                } else
                    findAllIds(it)
            }
    }
}