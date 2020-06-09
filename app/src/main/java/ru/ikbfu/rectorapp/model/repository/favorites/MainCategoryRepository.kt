package ru.ikbfu.rectorapp.model.repository.favorites

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import ru.ikbfu.rectorapp.model.data.db.dao.FavoritesDao
import ru.ikbfu.rectorapp.model.data.db.dao.SelectionCommitteeDao
import ru.ikbfu.rectorapp.model.data.db.entity.FavoriteEntity
import ru.ikbfu.rectorapp.model.data.mapper.SelectionCommitteeElementMapper
import ru.ikbfu.rectorapp.model.data.server.ApiIKBFU
import ru.ikbfu.rectorapp.model.data.server.model.ElementType
import ru.ikbfu.rectorapp.model.data.server.model.SelectionCommitteeElement
import ru.ikbfu.rectorapp.model.data.server.model.UnderlineType
import ru.ikbfu.rectorapp.model.system.SchedulersProvider
import javax.inject.Inject

class MainCategoryRepository @Inject constructor(
    private val schedulers: SchedulersProvider,
    private val mapper: SelectionCommitteeElementMapper,
    private val favoritesDao: FavoritesDao,
    private val selectionCommitteeDao: SelectionCommitteeDao
) {
    private var selectionElements: MutableList<SelectionCommitteeElement> =
        arrayListOf()
    private var idsToHide = arrayListOf<String>()

    fun loadData() =
        selectionCommitteeDao.getAll()
            .flattenAsFlowable { it }
            .map { mapper.toSelectionCommitteeElement(it) }
            .toList()
            .zipWith(favoritesDao.getAll(),
                BiFunction<List<SelectionCommitteeElement>, List<FavoriteEntity>, List<SelectionCommitteeElement>> { data, favorites ->
                    val ids = favorites.map { it.id }
                    data.apply { forEach { it.isFavorite = ids.contains(it.id) } }

                })
            .doOnSuccess { selectionElements = it.toMutableList() }
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

    fun collapseChildsOf(item: SelectionCommitteeElement): Single<List<SelectionCommitteeElement>> {
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
        return Single.just(selectionElements.filter { it.needToShow })
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

    fun checked(item: SelectionCommitteeElement) = Single.fromCallable {
        if (item.isFavorite)
            favoritesDao.insert(FavoriteEntity(item.id))
        else
            favoritesDao.delete(FavoriteEntity(item.id))
        val element = selectionElements.first { item.id == it.id }
        val index = selectionElements.indexOfFirst { item.id == it.id }
        selectionElements[index] = element.copy().apply {
            isFavorite = element.isFavorite
            isCollapsed = element.isCollapsed
            needToShow = element.needToShow
        }
        selectionElements.filter { it.needToShow }
    }
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
}