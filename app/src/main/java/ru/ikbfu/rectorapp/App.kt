package ru.ikbfu.rectorapp

import android.app.Application
import com.facebook.stetho.Stetho
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import net.danlew.android.joda.JodaTimeAndroid
import okhttp3.OkHttpClient
import ru.ikbfu.rectorapp.global.picasso.picassoAuthInstance
import ru.ikbfu.rectorapp.toothpick.AppInjector
import ru.ikbfu.rectorapp.toothpick.DI
import ru.ikbfu.rectorapp.toothpick.module.AppModule
import ru.ikbfu.rectorapp.toothpick.module.NetworkModule
import ru.ikbfu.rectorapp.toothpick.module.PICASSO_CLIENT
import timber.log.Timber
import toothpick.Toothpick
import toothpick.configuration.Configuration

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
        initTimber()
        initToothpick()
        initAppScope()
        initStetho()
        AppInjector.init(this)
        ObjectMapper().registerModule(KotlinModule())
        JodaTimeAndroid.init(this)
    }

    private fun initToothpick() {
        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        } else {
            Toothpick.setConfiguration(Configuration.forProduction().preventMultipleRootScopes())
        }
    }

    private fun initAppScope() {
        val openScope = Toothpick.openScope(DI.APP_SCOPE)
        openScope.installModules(AppModule(this))
        val networkScope = Toothpick.openScopes(DI.APP_SCOPE, DI.NETWORK_SCOPE)
        networkScope.installModules(NetworkModule(BuildConfig.ServerEndpoint))
        val provider = networkScope.getInstance(OkHttpClient::class.java, PICASSO_CLIENT)
        picassoAuthInstance(this, provider)
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    @Suppress("SpellCheckingInspection")
    private fun initStetho() {
        @Suppress("SpellCheckingInspection")
        if (BuildConfig.DEBUG) {
            val initializerBuilder = Stetho
                .newInitializerBuilder(this)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .build()
            Stetho.initialize(initializerBuilder)
        }
    }
}