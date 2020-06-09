package ru.ikbfu.rectorapp.presenter

import moxy.InjectViewState
import ru.ikbfu.rectorapp.Screens
import ru.ikbfu.rectorapp.global.BasePresenter
import ru.ikbfu.rectorapp.model.interactor.MainActivityInteractor
import ru.ikbfu.rectorapp.model.system.NavigationAction
import ru.ikbfu.rectorapp.model.system.NavigationActionRelay
import ru.ikbfu.rectorapp.presenter.view.MainActivityView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MainActivityPresenter @Inject constructor(
    private val interactor: MainActivityInteractor,
    private val actionNavigation: NavigationActionRelay,
    private val router: Router
) : BasePresenter<MainActivityView>() {

    override fun onFirstViewAttach() {
        actionNavigation.get().subscribe {
            when (it) {
                NavigationAction.LogOut -> authorize()
                else -> {
                }
            }
        }.bind()
    }

    private val authorized
        get() = interactor.authorized

    fun coldStart() {
        if (!authorized) authorize()
        else router.newRootScreen(Screens.FlowRootScreen)
    }

    private fun authorize() {
        router.newRootScreen(Screens.AuthScreen)
    }
}