package ru.ikbfu.rectorapp.ui.fragments.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import toothpick.Scope
import toothpick.Toothpick
import toothpick.config.Module
import javax.inject.Inject

abstract class MvpBaseFlowFragment : MvpBaseFragment() {

    abstract val containerId: Int

    private val currentFragment
        get() = childFragmentManager.findFragmentById(containerId) as? MvpBaseFragment


    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    protected val navigator: Navigator by lazy {
        object : SupportAppNavigator(
            this.activity,
            childFragmentManager,
            containerId
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
    }

    override fun installModules(scope: Scope) {
        scope.installModules(object : Module() {
            init {
                val cicerone = Cicerone.create()
                bind(ru.terrakok.cicerone.Router::class.java).toInstance(cicerone.router)
                bind(NavigatorHolder::class.java).toInstance(cicerone.navigatorHolder)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }


    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}