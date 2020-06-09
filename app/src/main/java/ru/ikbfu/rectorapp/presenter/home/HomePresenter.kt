package ru.ikbfu.rectorapp.presenter.home


import io.reactivex.android.schedulers.AndroidSchedulers
import ru.ikbfu.rectorapp.model.interactor.home.HomeInteractor
import moxy.InjectViewState
import ru.ikbfu.rectorapp.global.BasePresenter
import ru.ikbfu.rectorapp.model.data.server.model.SelectionCommitteeElement
import ru.ikbfu.rectorapp.model.system.NavigationAction
import ru.ikbfu.rectorapp.model.system.NavigationActionRelay
import ru.ikbfu.rectorapp.presenter.view.HomeView
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@InjectViewState
class HomePresenter @Inject constructor(
    private val interactor: HomeInteractor,
    private val actionNavigation: NavigationActionRelay
) : BasePresenter<HomeView>() {
    val authorized
        get() = interactor.authorized

    fun getMainData() =
        interactor.getMainData()
            .doOnSubscribe { viewState.showDownLoadProgress(true) }
            .doAfterTerminate { viewState.showDownLoadProgress() }
            .subscribe({
                viewState.initTree(it)
            }, {
                Timber.e(it)
            }).bind()

    fun getAllPriemApplications() = interactor.getAllPriemApplications()
        .subscribe({
            viewState.setupAllAplicationsCount(it)
        }, {
            Timber.e(it)
        }).bind()

    fun refreshData() = interactor.updatePriem()
        .doOnSubscribe {
            viewState.showRefreshProgress()
            viewState.showDownLoadProgress(true)

        }
        .doAfterTerminate { viewState.showDownLoadProgress() }
        .subscribe({
            viewState.setTreeData(it)
        }, {
            Timber.e(it)
        }).bind()

    fun collapseChildsOf(item: SelectionCommitteeElement) =
        interactor.collapseChildsOf(item)
            .subscribe({
                viewState.setTreeData(it)
            }, {
                Timber.e(it)
            }).bind()

    fun getUserInfo() {
        if (authorized) {
            viewState.setAvatar(interactor.name, interactor.surname, interactor.avatarUrl)
            viewState.setUserName(
                interactor.name.toUpperCase(Locale.getDefault()),
                interactor.surname.toUpperCase(Locale.getDefault())
            )
            viewState.setUserDivision(interactor.division)
            viewState.setUserActivity(interactor.activity)
        }
    }

    fun logout() = interactor.logout()
        .subscribe({
            actionNavigation.pushAction(NavigationAction.LogOut)
        }, {
            Timber.e(it)
        }).bind()

    fun collapseProfile(value: Boolean) {
        interactor.collapse(value)
        checkCollapsing()
    }

    fun checkCollapsing() = viewState.userProfileCollapsed(interactor.collapsed)

}
