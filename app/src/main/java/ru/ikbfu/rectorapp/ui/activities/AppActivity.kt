package ru.ikbfu.rectorapp.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import io.reactivex.disposables.Disposable
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.ikbfu.rectorapp.R
import ru.ikbfu.rectorapp.extensions.verifyStoragePermissions
import ru.ikbfu.rectorapp.global.CompositeDisposableComponent
import ru.ikbfu.rectorapp.global.CompositeDisposableComponentImpl
import ru.ikbfu.rectorapp.model.system.message.SystemMessageNotifier
import ru.ikbfu.rectorapp.presenter.MainActivityPresenter
import ru.ikbfu.rectorapp.presenter.view.MainActivityView
import ru.ikbfu.rectorapp.toothpick.DI
import ru.ikbfu.rectorapp.ui.fragments.base.MvpBaseFragment
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import toothpick.Toothpick
import javax.inject.Inject

class AppActivity : MvpAppCompatActivity(), MainActivityView,
    CompositeDisposableComponent by CompositeDisposableComponentImpl() {

    private val scope = Toothpick.openScopes(DI.APP_SCOPE, DI.NETWORK_SCOPE)

    @Inject
    @InjectPresenter
    lateinit var presenter: MainActivityPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    @Inject
    lateinit var systemMessageNotifier: SystemMessageNotifier

    @Inject
    lateinit var navigationHolder: NavigatorHolder

    private var disposeSystemMessage: Disposable? = null

    private val currentFragment: MvpBaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.activity_container) as? MvpBaseFragment


    private val navigator: Navigator = object :
        SupportAppNavigator(
            this,
            R.id.activity_container
        ) {
        override fun setupFragmentTransaction(
            command: Command?,
            currentFragment: Fragment?,
            nextFragment: Fragment?,
            fragmentTransaction: FragmentTransaction?
        ) {
            fragmentTransaction?.setReorderingAllowed(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, scope)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        verifyStoragePermissions()
        if (savedInstanceState == null) {
            presenter.coldStart()
        }
    }

    private fun showToast(message: String) {
        if (message.isNotBlank()) Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationHolder.setNavigator(navigator)
    }

    override fun onResume() {
        super.onResume()
        subscribeSystemNotifier()
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        disposeSystemMessage?.dispose()
        super.onPause()
    }

    private fun subscribeSystemNotifier() {
        disposeSystemMessage = systemMessageNotifier
            .notifier
            .subscribe {
                when (it.type) {
                }
            }
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }
}
