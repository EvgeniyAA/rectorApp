package ru.ikbfu.rectorapp.model.repository.staff

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import ru.ikbfu.rectorapp.model.data.db.dao.FavoritesDao
import ru.ikbfu.rectorapp.model.data.db.entity.FavoriteEntity
import ru.ikbfu.rectorapp.model.data.server.ApiIKBFU
import ru.ikbfu.rectorapp.model.data.server.model.*
import ru.ikbfu.rectorapp.model.system.SchedulersProvider
import javax.inject.Inject

class StaffRepository @Inject constructor(
    private val schedulers: SchedulersProvider,
    private val apiIKBFU: ApiIKBFU,
    private val favoritesDao: FavoritesDao
) {
    private var params: MutableList<ShowParam> = mutableListOf()
    private var staffHierarchy: MutableList<StaffHierarchyItem> = mutableListOf()
    private var idsToHide = arrayListOf<String>()
    fun getHierarchyData(): Completable = apiIKBFU.getStaff()
    //    Single.just(listOf(
    //    StaffHierarchyItem("1a","ПСС", 995, null,ElementType.PARENT_DATA, listOf("2a","4a","6a","9a"),UnderlineType.FULL,"#15CBBD"),
    //    StaffHierarchyItem("2a","Авиационный учебный центр", 11, null,ElementType.CHILD_DATA, listOf("3a"),UnderlineType.PART),
    //    StaffHierarchyItem("3a","Преподаватель", 11, null,ElementType.ROOT_DATA, listOf(),UnderlineType.NONE),
    //    StaffHierarchyItem("4a","До реорганизации", 1, null,ElementType.CHILD_DATA, listOf("5a"),UnderlineType.PART),
    //    StaffHierarchyItem("5a","Старший преподаватель", 1, null,ElementType.ROOT_DATA, listOf(),UnderlineType.NONE),
    //    StaffHierarchyItem("6a","Европейская бизнес - школа", 7, null,ElementType.CHILD_DATA, listOf("7a"),UnderlineType.PART),
    //    StaffHierarchyItem("7a","Доцент с ученой степенью к.н.", 5, null,ElementType.ROOT_DATA, listOf(),UnderlineType.NONE, type= StaffType.SCIENCE_KANDIDAT),
    //    StaffHierarchyItem("8a","Старший преподаватель", 2, null,ElementType.ROOT_DATA, listOf(),UnderlineType.NONE),
    //    StaffHierarchyItem("9a","Инженерно-технический институт", 1, null,ElementType.CHILD_DATA, listOf("10a"),UnderlineType.PART),
    //    StaffHierarchyItem("10a","Директор института", 1, null,ElementType.ROOT_DATA, listOf(),UnderlineType.NONE),
    //    StaffHierarchyItem("11a","ИНСТИТУТ ГУМАНИТАРНЫХ НАУК", 138, null,ElementType.CHILD_DATA, listOf("12a","13a","14a","15a","16a","17a"),UnderlineType.PART),
    //    StaffHierarchyItem("12a","Ассистент", 13, null,ElementType.ROOT_DATA, listOf(),UnderlineType.NONE),
    //    StaffHierarchyItem("13a","Директор института", 1, null,ElementType.ROOT_DATA, listOf(),UnderlineType.NONE),
    //    StaffHierarchyItem("14a","Доцент без уч. степени", 2, null,ElementType.ROOT_DATA, listOf(),UnderlineType.NONE, type = StaffType.PROFESSOR),
    //    StaffHierarchyItem("15a","Профессор(консультант)", 3, null,ElementType.ROOT_DATA, listOf(),UnderlineType.NONE, type = StaffType.PROFESSOR),
    //    StaffHierarchyItem("16a","Профессор(консультант) ВМУ", 2, null,ElementType.ROOT_DATA, listOf(),UnderlineType.NONE, type = StaffType.PROFESSOR),
    //    StaffHierarchyItem("17a","Профессор с уч. степенью д.н.", 28, null,ElementType.ROOT_DATA, listOf(),UnderlineType.NONE, type = StaffType.PROFESSOR)
    //))
        .zipWith(favoritesDao.getAll(),
            BiFunction<List<StaffHierarchyItem>, List<FavoriteEntity>, List<StaffHierarchyItem>> { data, favorites ->
                val ids = favorites.map { it.id }
                data.apply { forEach { it.isFavorite = ids.contains(it.id) } }

            })
        .doOnSuccess { staffHierarchy = it.toMutableList() }
        .ignoreElement()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())

    fun getParamsData() = Single.just(
        listOf(ShowParam("1","Все", true,StaffType.ALL),
            ShowParam("2","Кандидаты наук (к.н.)", true,StaffType.SCIENCE_KANDIDAT),
            ShowParam("3","Доктора наук(д.н.)", true, StaffType.SCIENCE_DOCTOR),
            ShowParam("4","Профессора", true, StaffType.PROFESSOR),
            ShowParam("5","Доценты", true, StaffType.DOCENT))
    )
        .doOnSuccess { params = it.toMutableList() }
        .ignoreElement()

    fun getParams() = params

    fun getHierarchy() = staffHierarchy

    fun collapseChildsOf(item: StaffHierarchyItem): Single<List<StaffHierarchyItem>> {
        val element = staffHierarchy.first { item.id == it.id }
        val index = staffHierarchy.indexOfFirst { item.id == it.id }
        staffHierarchy[index] = element.copy().apply {
            isCollapsed = !element.isCollapsed
            isFavorite = element.isFavorite
            needToShow = element.needToShow
        }
        findAllIds(item)
        staffHierarchy.forEach {
            if (idsToHide.contains(it.id) && item.id != it.id) {
                it.needToShow = !it.needToShow
            }
        }
        idsToHide.clear()
        return Single.just(staffHierarchy.filter { it.needToShow })
    }


    fun findAllIds(item: StaffHierarchyItem) {
        idsToHide.add(item.id)
        if (!item.childIds.isNullOrEmpty())
            staffHierarchy.filter { item.childIds.contains(it.id) }.forEach {
                if (it.isCollapsed) {
                    idsToHide.add(it.id)
                    return@forEach
                } else
                    findAllIds(it)
            }
    }

    fun checked(item: StaffHierarchyItem) = Single.fromCallable {
        if (item.isFavorite)
            favoritesDao.insert(FavoriteEntity(item.id))
        else
            favoritesDao.delete(FavoriteEntity(item.id))
        val element = staffHierarchy.first { item.id == it.id }
        val index = staffHierarchy.indexOfFirst { item.id == it.id }
        staffHierarchy[index] = element.copy().apply {
            isFavorite = element.isFavorite
            isCollapsed = element.isCollapsed
            needToShow = element.needToShow
        }
        staffHierarchy.filter { it.needToShow }
    }
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
}