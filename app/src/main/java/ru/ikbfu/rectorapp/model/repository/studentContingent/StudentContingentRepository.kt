package ru.ikbfu.rectorapp.model.repository.studentContingent

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import ru.ikbfu.rectorapp.model.data.db.dao.FavoritesDao
import ru.ikbfu.rectorapp.model.data.db.entity.FavoriteEntity
import ru.ikbfu.rectorapp.model.data.server.ApiIKBFU
import ru.ikbfu.rectorapp.model.data.server.model.Graduate
import ru.ikbfu.rectorapp.model.data.server.model.Student
import ru.ikbfu.rectorapp.model.system.SchedulersProvider
import javax.inject.Inject

class StudentContingentRepository @Inject constructor(
    private val schedulers: SchedulersProvider,
    private val apiIKBFU: ApiIKBFU,
    private val favoritesDao: FavoritesDao
) {
    private var currentStudents: MutableList<Student> = mutableListOf()
    private var graduates: MutableList<Graduate> = mutableListOf()
    private var idsToHide = arrayListOf<String>()
    private var graduateYears = 0 to 0

    fun getCurrentStudents() = currentStudents
    fun getGraduates() = graduates
    fun getGraduatesYears() = graduateYears

    fun getGraduatesData() = apiIKBFU.getGraduates()
        .zipWith(favoritesDao.getAll(),
            BiFunction<List<Graduate>, List<FavoriteEntity>, List<Graduate>> { data, favorites ->
                val ids = favorites.map { it.id }
                data.apply { forEach { it.isFavorite = ids.contains(it.id) } }

            })
        .doOnSuccess { graduates = it.toMutableList() }
        .ignoreElement()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())

    fun getCurrentStudentsData() = apiIKBFU.getCurrentStudents()
        .zipWith(favoritesDao.getAll(),
            BiFunction<List<Student>, List<FavoriteEntity>, List<Student>> { data, favorites ->
                val ids = favorites.map { it.id }
                data.apply { forEach { it.isFavorite = ids.contains(it.id) } }

            })
        .doOnSuccess { currentStudents = it.toMutableList() }
        .ignoreElement()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())

    fun getGraduateYearsData() = apiIKBFU.getGraduatedYears()
        .doOnSuccess { graduateYears = it.first() to it.last() }
        .ignoreElement()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())

    fun checked(item: Student) = Single.fromCallable {
        if (item.isFavorite)
            favoritesDao.insert(FavoriteEntity(item.id))
        else
            favoritesDao.delete(FavoriteEntity(item.id))
        val element = currentStudents.first { item.id == it.id }
        val index = currentStudents.indexOfFirst { item.id == it.id }
        currentStudents[index] = element.copy().apply {
            isFavorite = element.isFavorite
            isCollapsed = element.isCollapsed
            needToShow = element.needToShow
        }
        currentStudents.filter { it.needToShow }
    }
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())

    fun checkedGraduate(item: Graduate) = Completable.fromAction {
        if (item.isFavorite)
            favoritesDao.insert(FavoriteEntity(item.id))
        else
            favoritesDao.delete(FavoriteEntity(item.id))

        graduates.first { it.id == item.id }.isFavorite = item.isFavorite
    }
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())

    fun collapseChildsOf(item: Student): Single<List<Student>> {
        val element = currentStudents.first { item.id == it.id }
        val index = currentStudents.indexOfFirst { item.id == it.id }
        currentStudents[index] = element.copy().apply {
            isCollapsed = !element.isCollapsed
            isFavorite = element.isFavorite
            needToShow = element.needToShow
        }
        findAllIds(item)
        currentStudents.forEach {
            if (idsToHide.contains(it.id) && item.id != it.id) {
                it.needToShow = !it.needToShow
            }
        }
        idsToHide.clear()
        return Single.just(currentStudents.filter { it.needToShow })
    }


    fun findAllIds(item: Student) {
        idsToHide.add(item.id)
        if (!item.childIds.isNullOrEmpty())
            currentStudents.filter { item.childIds.contains(it.id) }.forEach {
                if (it.isCollapsed) {
                    idsToHide.add(it.id)
                    return@forEach
                } else
                    findAllIds(it)
            }
    }
}