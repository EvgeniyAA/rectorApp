package ru.ikbfu.rectorapp.ui.fragments.auth

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_login.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.ikbfu.rectorapp.R
import ru.ikbfu.rectorapp.extensions.hideKeyboardOnTouchOutside
import ru.ikbfu.rectorapp.extensions.onImeActionDoneClicks
import ru.ikbfu.rectorapp.presenter.auth.AuthPresenter
import ru.ikbfu.rectorapp.presenter.view.AuthView
import ru.ikbfu.rectorapp.ui.fragments.base.MvpBaseFragment

class AuthFragment : MvpBaseFragment(), AuthView {
    override val layout: Int = R.layout.fragment_login

    companion object {
        fun newInstance() = AuthFragment()
    }

    @InjectPresenter
    lateinit var presenter: AuthPresenter

    @ProvidePresenter
    fun providePresenter() = scope
        .getInstance(AuthPresenter::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scroll_view.hideKeyboardOnTouchOutside(context)
        setClickListeners()
    }

    override fun showDownLoadProgress(isVisible: Boolean) {
        showProgressDialog(isVisible)
    }

    private fun setClickListeners(){
        enter_button.setOnClickListener { presenter.checkedLogin(user_name.text.toString(), user_password.text.toString()) }
        user_password.onImeActionDoneClicks().subscribe { presenter.checkedLogin(user_name.text.toString(), user_password.text.toString()) }.bind()
    }
}