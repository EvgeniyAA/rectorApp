package ru.ikbfu.rectorapp.toothpick

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import ru.ikbfu.rectorapp.App
import toothpick.Scope
import toothpick.Toothpick

class AppInjector {
    companion object {
        fun init(app: App) {
            app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                override fun onActivityPaused(activity: Activity?) {
                }

                override fun onActivityResumed(activity: Activity?) {
                }

                override fun onActivityStarted(activity: Activity?) {
                }

                override fun onActivityDestroyed(activity: Activity?) {
                    if (activity is Injectable) {
                        Toothpick.closeScope(activity)
                    }
                }

                override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
                }

                override fun onActivityStopped(activity: Activity?) {
                }

                override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                    handleActivity(activity)
                }
            })
        }

        private fun handleActivity(activity: Activity?) {
            if (activity is Injectable) {
                inject(activity)
            }

            (activity as? FragmentActivity)?.supportFragmentManager?.registerFragmentLifecycleCallbacks(
                object : FragmentManager.FragmentLifecycleCallbacks() {
                    override fun onFragmentPreCreated(
                        fm: FragmentManager,
                        f: Fragment,
                        savedInstanceState: Bundle?
                    ) {
                        if (f is Injectable) {
                            inject(f)
                        }
                    }

                    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
                        if (f is Injectable) {
                            Toothpick.closeScope(f)
                        }
                    }
                }, true
            )
        }

        private fun inject(f: Injectable) {
            val scope: Scope = Toothpick.openScopes(*f.getScopes())
            f.getModules()?.let {
                scope.installModules(*it)
            }
            Toothpick.inject(f, scope)
        }
    }
}