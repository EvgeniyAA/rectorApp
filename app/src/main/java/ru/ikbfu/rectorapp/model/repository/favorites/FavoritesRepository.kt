package ru.ikbfu.rectorapp.model.repository.favorites

import io.reactivex.Single
import ru.ikbfu.rectorapp.extensions.unify
import ru.ikbfu.rectorapp.model.data.db.dao.FavoritesDao
import ru.ikbfu.rectorapp.model.data.db.dao.SelectionCommitteeDao
import ru.ikbfu.rectorapp.model.data.mapper.SelectionCommitteeElementMapper
import ru.ikbfu.rectorapp.model.data.server.ApiIKBFU
import ru.ikbfu.rectorapp.model.data.server.model.*
import ru.ikbfu.rectorapp.model.system.SchedulersProvider
import javax.inject.Inject

class FavoritesRepository @Inject constructor(
    private val api: ApiIKBFU,
    private val schedulers: SchedulersProvider,
    private val mapper: SelectionCommitteeElementMapper,
    private val favoritesDao: FavoritesDao,
    private val selectionCommitteeDao: SelectionCommitteeDao
) {
    private var selectionElements: MutableList<FavoriteData> =
        arrayListOf()

    fun getFavorites() = favoritesDao.getAll()
        .map { idsEntity ->
            val ids = idsEntity.map { it.id }
            selectionElements.filter { ids.contains(it.id) }
        }
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())

    fun filterFavorites(text: String) = getFavorites()
        .flattenAsFlowable { it }
        .filter { it.title.unify().contains(text.unify()) }
        .toList()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())

    fun getTreeData() = selectionCommitteeDao.getAll()
        .flattenAsFlowable { it }
        .map { mapper.toFavorite(it) }
        .toObservable()
        .toList()
        .doOnSuccess {
            selectionElements.clear()
            selectionElements = it.toMutableList()
        }
        .ignoreElement()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())

    fun getStaffData() = api.getStaff()
        .flattenAsFlowable { it }
        .map { mapper.toFavorite(it) }
        .toObservable()
        .toList()
        .doOnSuccess { selectionElements = (selectionElements+it.toMutableList()).toMutableList() }
        .ignoreElement()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())

    fun getGraduateData() = api.getGraduates()
        .flattenAsFlowable { it }
        .map { mapper.toFavorite(it) }
        .toObservable()
        .toList()
        .doOnSuccess { selectionElements = (selectionElements+it.toMutableList()).toMutableList() }
        .ignoreElement()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())

    fun getStudents() = api.getCurrentStudents()
        .flattenAsFlowable { it }
        .map { mapper.toFavorite(it) }
        .toObservable()
        .toList()
        .doOnSuccess { selectionElements = (selectionElements+it.toMutableList()).toMutableList() }
        .ignoreElement()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
}