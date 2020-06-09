package ru.ikbfu.rectorapp.model.interactor.staff

import ru.ikbfu.rectorapp.model.data.server.model.StaffHierarchyItem
import ru.ikbfu.rectorapp.model.repository.staff.StaffRepository
import javax.inject.Inject

class StaffInteractor @Inject constructor(private val repository: StaffRepository) {
    fun getAll() = repository.getHierarchyData().andThen(repository.getParamsData())
    fun getParams() = repository.getParams()
    fun getHierarchy() = repository.getHierarchy()
    fun checked(item: StaffHierarchyItem) = repository.checked(item)
    fun collapseChildsOf(item: StaffHierarchyItem) = repository.collapseChildsOf(item)
}