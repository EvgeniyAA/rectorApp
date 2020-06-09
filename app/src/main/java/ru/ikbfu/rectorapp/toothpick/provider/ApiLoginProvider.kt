package ru.ikbfu.rectorapp.toothpick.provider

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import ru.ikbfu.rectorapp.model.data.server.ApiIKBFU
import ru.ikbfu.rectorapp.toothpick.qualifier.LoginOkHttpClient
import ru.ikbfu.rectorapp.toothpick.qualifier.ServerUrl
import timber.log.Timber
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Provider

class ApiLoginProvider @Inject constructor(
    @LoginOkHttpClient private val client: OkHttpClient,
    @ServerUrl private val url: String
) : Provider<ApiIKBFU> {
    override fun get(): ApiIKBFU = with( Retrofit.Builder()) {
        Timber.d("Creating ApiIKBFU")
        addConverterFactory(JacksonConverterFactory.create())
        addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        callbackExecutor(Executors.newFixedThreadPool(3))
        client(client)
        baseUrl(url)
        build()
    }.create(ApiIKBFU::class.java)
}