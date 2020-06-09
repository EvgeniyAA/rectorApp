package ru.ikbfu.rectorapp.model.repository.science

import io.reactivex.Single
import ru.ikbfu.rectorapp.extensions.unify
import ru.ikbfu.rectorapp.model.data.server.ApiIKBFU
import ru.ikbfu.rectorapp.model.data.server.model.ScienceItem
import ru.ikbfu.rectorapp.model.system.SchedulersProvider
import javax.inject.Inject

class ScienceRepository @Inject constructor(
    private val apiIKBFU: ApiIKBFU,
    private val schedulers: SchedulersProvider
) {
    private var scienceData: ArrayList<ScienceItem> = arrayListOf()
    fun getScienceData() = apiIKBFU.getScience()
        .doOnSuccess { scienceData = ArrayList(it) }
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())

    fun filterScienceData(text: String) = Single.just(scienceData)
        .flattenAsFlowable { it }
        .filter { it.title.unify().contains(text.unify()) || it.desc.unify().contains(text.unify()) }
        .toList()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
}