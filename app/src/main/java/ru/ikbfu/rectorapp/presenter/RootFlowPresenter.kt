package ru.ikbfu.rectorapp.presenter

import moxy.InjectViewState
import ru.ikbfu.rectorapp.Screens
import ru.ikbfu.rectorapp.global.BasePresenter
import ru.ikbfu.rectorapp.model.interactor.MainActivityInteractor
import ru.ikbfu.rectorapp.model.system.FlowRouter
import ru.ikbfu.rectorapp.presenter.view.RootFlowView
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class RootFlowPresenter @Inject constructor(
    private val router: Router
) : BasePresenter<RootFlowView>() {
    fun goToHome() {
            router.newRootScreen(Screens.HomeScreen)
    }

    fun goToGroup() {
            router.newRootScreen(Screens.GroupScreen)
    }
    fun goToFavorites() {
            router.newRootScreen(Screens.FavoritesFlowScreen)
    }
}