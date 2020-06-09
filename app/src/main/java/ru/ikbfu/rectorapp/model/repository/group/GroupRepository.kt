package ru.ikbfu.rectorapp.model.repository.group

import io.reactivex.Single
import ru.ikbfu.rectorapp.extensions.unify
import ru.ikbfu.rectorapp.model.data.server.model.Category
import ru.ikbfu.rectorapp.model.data.server.model.DataType
import ru.ikbfu.rectorapp.model.system.SchedulersProvider
import javax.inject.Inject

class GroupRepository @Inject constructor(
    private val schedulers: SchedulersProvider
) {
    private var categories: ArrayList<Category> = arrayListOf()
    fun getData() = Single.just(
        listOf(
            Category("Приемная комиссия", DataType.SELECTION_COMMITTEE),
            Category("Контингент студентов", DataType.STUDENT_CONTINGENT),
            Category("Штат", DataType.STAFF),
            Category("Наука", DataType.SCIENCE)
        )
    )
        .doOnSuccess { categories = ArrayList(it) }
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())

    fun filterCategories(text: String) = Single.just(categories)
        .flattenAsFlowable { it }
        .filter { it.title.unify().contains(text.unify()) }
        .toList()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
}