package ru.ikbfu.rectorapp.toothpick.module

import okhttp3.OkHttpClient
import ru.ikbfu.rectorapp.model.data.db.AppDatabase
import ru.ikbfu.rectorapp.model.data.db.dao.FavoritesDao
import ru.ikbfu.rectorapp.model.data.db.dao.SelectionCommitteeDao
import ru.ikbfu.rectorapp.model.data.mapper.SelectionCommitteeElementMapper
import ru.ikbfu.rectorapp.model.data.server.ApiIKBFU
import ru.ikbfu.rectorapp.toothpick.provider.*
import ru.ikbfu.rectorapp.toothpick.qualifier.LoginOkHttpClient
import ru.ikbfu.rectorapp.toothpick.qualifier.ServerUrl
import timber.log.Timber
import toothpick.config.Module

const val PICASSO_CLIENT = "picasso"
class NetworkModule(serverUrl: String) : Module() {

    init {
        bind(String::class.java)
            .withName(ServerUrl::class.java)
            .toInstance(serverUrl)
        bind(OkHttpClient::class.java)
            .withName(LoginOkHttpClient::class.java)
            .toProvider(LoginOkHttpClientProvider::class.java)
            .providesSingleton()

        bind(ApiIKBFU::class.java)
            .toProvider(ApiLoginProvider::class.java)
            .providesSingleton()
        bind(OkHttpClient::class.java)
            .withName(PICASSO_CLIENT)
            .toProvider(PicassoOkHttpClient::class.java).providesSingleton()

        Timber.d("init domain and data layer")
            // bind(EventsRepository::class.java).to(EventsRepository::class.java).singletonInScope()
            // bind(EventsInteractor::class.java).to(EventsInteractor::class.java).singletonInScope()

        bind(AppDatabase::class.java).toProvider(DatabaseProvider::class.java).providesSingleton()
        bind(FavoritesDao::class.java).toProvider(FavoriteDaoProvider::class.java).providesSingleton()
        bind(SelectionCommitteeDao::class.java).toProvider(SelectionCommitteeDaoProvider::class.java).providesSingleton()

        bind(SelectionCommitteeElementMapper::class.java).toInstance(SelectionCommitteeElementMapper())
    }
}