package ru.ikbfu.rectorapp.model.interactor.favorites

import io.reactivex.Single
import ru.ikbfu.rectorapp.model.data.server.model.SelectionCommitteeElement
import ru.ikbfu.rectorapp.model.repository.favorites.MainCategoryRepository
import javax.inject.Inject

class MainCategoryInteractor @Inject constructor(private val repository: MainCategoryRepository) {

    fun loadData() = repository.loadData().andThen(repository.filterAndCollapse())

    fun collapseChildsOf(item: SelectionCommitteeElement) = repository.collapseChildsOf(item)
    fun checked(item: SelectionCommitteeElement) = repository.checked(item)
}