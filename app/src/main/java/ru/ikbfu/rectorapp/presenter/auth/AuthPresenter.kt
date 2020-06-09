package ru.ikbfu.rectorapp.presenter.auth

import moxy.InjectViewState
import ru.ikbfu.rectorapp.Screens
import ru.ikbfu.rectorapp.global.BasePresenter
import ru.ikbfu.rectorapp.model.interactor.auth.AuthInteractor
import ru.ikbfu.rectorapp.presenter.view.AuthView
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class AuthPresenter @Inject constructor(
    private val router: Router,
    private val interactor: AuthInteractor
) : BasePresenter<AuthView>() {

    fun checkedLogin(email: String, password: String) {
        if (invalidEmail(email)) return
        if (invalidPassword(password)) return
        interactor.login(email, password)
            .doOnSubscribe { viewState.showDownLoadProgress(true) }
            .doAfterTerminate { viewState.showDownLoadProgress() }
            .subscribe({
                router.newRootScreen(Screens.FlowRootScreen)
            },{
                Timber.e(it)
            }).bind()

    }

    private fun invalidEmail(email: String) = email.isEmpty()
    private fun invalidPassword(password: String) = password.isEmpty()

}