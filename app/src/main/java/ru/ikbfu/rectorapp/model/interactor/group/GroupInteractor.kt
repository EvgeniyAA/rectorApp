package ru.ikbfu.rectorapp.model.interactor.group

import ru.ikbfu.rectorapp.model.repository.group.GroupRepository
import javax.inject.Inject

class GroupInteractor @Inject constructor(private val repository: GroupRepository) {

    fun getData() = repository.getData()
    fun filterCategories(text: String) = repository.filterCategories(text)
}