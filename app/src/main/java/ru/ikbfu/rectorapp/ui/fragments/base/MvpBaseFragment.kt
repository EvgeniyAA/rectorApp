package ru.ikbfu.rectorapp.ui.fragments.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import moxy.MvpAppCompatFragment
import ru.ikbfu.rectorapp.extensions.objectScopeName
import ru.ikbfu.rectorapp.global.CompositeDisposableComponent
import ru.ikbfu.rectorapp.global.CompositeDisposableComponentImpl
import ru.ikbfu.rectorapp.toothpick.DI
import timber.log.Timber
import toothpick.Scope
import toothpick.Toothpick

private const val STATE_SCOPE_NAME = "state_scope_name"
private const val PROGRESS_TAG = "bf_progress"

abstract class MvpBaseFragment : MvpAppCompatFragment(),
    CompositeDisposableComponent by CompositeDisposableComponentImpl() {
    abstract val layout: Int
        @LayoutRes get

    private val parentScopeName: String by lazy {
        (parentFragment as? MvpBaseFragment)?.fragmentScopeName
            ?: DI.NETWORK_SCOPE
    }

    private var instanceStateSaved: Boolean = false

    private lateinit var fragmentScopeName: String

    protected lateinit var scope: Scope
        private set

    protected open fun installModules(scope: Scope) {}

    private val TAG: String
        get() = javaClass.simpleName

    private var mLifeCycleLogsEnabled = true

    protected fun enableLifeCycleLogs(enable: Boolean) {
        mLifeCycleLogsEnabled = enable
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        log("onViewCreated()")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        log("onViewStateRestored()")
    }

    override fun onStart() {
        super.onStart()
        log("onStart()")
    }

    override fun onPause() {
        showProgressDialog(false)
        super.onPause()
        log("onPause()")
    }

    override fun onStop() {
        super.onStop()
        log("onStop()")
    }

    open fun setToolbar(toolbar: Toolbar): ActionBar? {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        return (activity as AppCompatActivity).supportActionBar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentScopeName = savedInstanceState?.getString(STATE_SCOPE_NAME) ?: objectScopeName()
        if (Toothpick.isScopeOpen(fragmentScopeName)) {
            Timber.d("Get exist UI scope: $fragmentScopeName")
            scope = Toothpick.openScope(fragmentScopeName)
        } else {
            Timber.d("Init new UI scope: $fragmentScopeName")
            scope = Toothpick.openScopes(parentScopeName, fragmentScopeName)
            installModules(scope)
        }
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            log("onCreate(): fragment re-created from savedInstanceState")
        } else {
            log("onCreate(): fragment created anew")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout, container, false)
    }

    override fun onResume() {
        super.onResume()
        instanceStateSaved = false
        log("onResume()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        log("onDestroyView()")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        instanceStateSaved = true
        outState.putString(STATE_SCOPE_NAME, fragmentScopeName)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (needCloseScope()) {
            Timber.d("Destroy UI scope: $fragmentScopeName")
            Toothpick.closeScope(scope.name)
        }
        clear()
        log("onDestroy()")
    }

    private fun isRealRemoving(): Boolean =
        (isRemoving && !instanceStateSaved) ||
                ((parentFragment as? MvpBaseFragment)?.isRealRemoving() ?: false)

    private fun needCloseScope(): Boolean =
        when {
            activity?.isChangingConfigurations == true -> false
            activity?.isFinishing == true -> true
            else -> isRealRemoving()
        }

    open fun onBackPressed() {}

    private fun log(log: String) {
        if (mLifeCycleLogsEnabled) {
            Timber.d("$TAG - $log")
        }
    }

    fun showProgressDialog(progress: Boolean) {
        if (!isAdded) return
        val fragment = childFragmentManager.findFragmentByTag(PROGRESS_TAG)
        if (fragment != null && !progress) {
            (fragment as ProgressDialog).dismissAllowingStateLoss()
            childFragmentManager.executePendingTransactions()
        } else if (fragment == null && progress) {
            ProgressDialog().show(childFragmentManager, PROGRESS_TAG)
            childFragmentManager.executePendingTransactions()
        }
    }
}