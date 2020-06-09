package ru.ikbfu.rectorapp.toothpick.module

import android.content.Context
import com.jakewharton.rxrelay2.PublishRelay
import ru.ikbfu.rectorapp.model.data.storage.CommonPrefs
import ru.ikbfu.rectorapp.model.data.storage.CommonPrefsProvider
import ru.ikbfu.rectorapp.model.system.*
import ru.ikbfu.rectorapp.model.system.message.SystemMessageNotifier
import ru.ikbfu.rectorapp.toothpick.qualifier.groupNavigation
import ru.ikbfu.rectorapp.toothpick.qualifier.studentNavigation
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import timber.log.Timber
import toothpick.config.Module

class AppModule(context: Context) : Module() {
    init {
        //        Global
        Timber.d("Creating Global dependencies...")
        bind(Context::class.java).toInstance(context)
        bind(SchedulersProvider::class.java).toInstance(AppSchedulers())
        bind(ExecutorsProvider::class.java).toInstance(AppExecutors())
        bind(ResourceManager::class.java).singleton()
        bind(SystemMessageNotifier::class.java).toInstance(SystemMessageNotifier())
        bind(CommonPrefs::class.java).to(CommonPrefsProvider::class.java).singleton()
        bind(NavigationActionRelay::class.java).toInstance(NavigationActionRelay(PublishRelay.create()))
        bind(NavigationActionRelay::class.java).withName(groupNavigation::class.java).toInstance(NavigationActionRelay(PublishRelay.create()))
        bind(NavigationActionRelay::class.java).withName(studentNavigation::class.java).toInstance(NavigationActionRelay(PublishRelay.create()))
        //       Database
        // Timber.d("Creating Database...")
        // cicerone
        // AuthHolder
        Timber.d("cicerone global router")
        val cicerone = Cicerone.create()
        bind(Router::class.java).toInstance(cicerone.router)
        bind(NavigatorHolder::class.java).toInstance(cicerone.navigatorHolder)
    }
}