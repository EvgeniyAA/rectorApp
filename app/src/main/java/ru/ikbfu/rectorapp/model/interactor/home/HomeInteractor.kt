package ru.ikbfu.rectorapp.model.interactor.home

import io.reactivex.Single
import ru.ikbfu.rectorapp.model.data.server.model.SelectionCommitteeElement
import ru.ikbfu.rectorapp.model.repository.home.HomeRepository
import javax.inject.Inject

class HomeInteractor @Inject constructor(private val repository: HomeRepository) {
    val authorized
        get() = repository.authorized

    val collapsed
    get() = repository.user.isProfileCollapsed

    val name: String
        get() = repository.user.firstName ?: "A"

    val activity: String
        get() = repository.user.activity ?: "A"

    val division: String
        get() = repository.user.division

    val surname: String
        get() = repository.user.lastName ?: "A"

    val avatarUrl: String
        get() = repository.user.avatarUrl

    fun logout() = repository.logout()

    fun collapse(value: Boolean) = repository.collapse(value)

    fun getMainData() = repository.getMainData().andThen(repository.filterAndCollapse())

    fun collapseChildsOf(item: SelectionCommitteeElement): Single<List<SelectionCommitteeElement>> = repository.collapseChildsOf(item)

    fun getAllPriemApplications() = repository.getAllPriemApplications()

    fun updatePriem() = repository.updatePriem().andThen(repository.filterAndCollapse())
}