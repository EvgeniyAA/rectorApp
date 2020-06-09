package ru.ikbfu.rectorapp.presenter.staff

import moxy.InjectViewState
import ru.ikbfu.rectorapp.Screens
import ru.ikbfu.rectorapp.global.BasePresenter
import ru.ikbfu.rectorapp.model.interactor.staff.StaffInteractor
import ru.ikbfu.rectorapp.model.system.FlowRouter
import ru.ikbfu.rectorapp.model.system.NavigationAction
import ru.ikbfu.rectorapp.model.system.NavigationActionRelay
import ru.ikbfu.rectorapp.presenter.view.StaffView
import ru.ikbfu.rectorapp.toothpick.qualifier.groupNavigation
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class StaffPresenter @Inject constructor(
    private val interactor: StaffInteractor,
    private val router: Router,
    @groupNavigation
    private val actionNavigation: NavigationActionRelay
) : BasePresenter<StaffView>() {

    override fun onFirstViewAttach() {
        interactor.getAll()
            .doOnSubscribe { viewState.showDownLoadProgress(true) }
            .doAfterTerminate { viewState.showDownLoadProgress() }
            .subscribe({
                viewState.tabSwitch()
            },{
                Timber.e(it)
            }).bind()
    }

    fun showParameters() = router.navigateTo(Screens.StaffParametersScreen)
    fun showHierarchy() = router.navigateTo(Screens.StaffHierarchyScreen)
    fun onBackPressed() = actionNavigation.pushAction(NavigationAction.Back)
}