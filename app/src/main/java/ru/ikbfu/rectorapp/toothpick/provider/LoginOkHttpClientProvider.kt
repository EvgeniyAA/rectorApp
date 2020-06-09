package ru.ikbfu.rectorapp.toothpick.provider

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import ru.ikbfu.rectorapp.BuildConfig
import ru.ikbfu.rectorapp.model.data.storage.CommonPrefs
import ru.ikbfu.rectorapp.utils.VALID_KEY
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider

class LoginOkHttpClientProvider @Inject constructor(
    private val context: Context,
    private val prefs: CommonPrefs
) : Provider<OkHttpClient> {

    companion object {
        const val CONNECTION_TIMEOUT = 1000L * 90
        const val READ_TIMEOUT = 1000L * 90
        private const val AUTHORIZATION_HEADER = "Authorization"
    }

    override fun get(): OkHttpClient = with(OkHttpClient.Builder()) {
        connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
        protocols(listOf(Protocol.HTTP_1_1))
        addInterceptor { chain ->

            val builder = chain.request().newBuilder()
            if (chain.request().header(AUTHORIZATION_HEADER) == null) {
                builder.addHeader(AUTHORIZATION_HEADER, prefs.token ?: "")
            }

            val response = chain.proceed(builder.build())
            response
        }
        addNetworkInterceptor(ChuckInterceptor(context))
        if (BuildConfig.DEBUG) {
            addNetworkInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        }
        build()
    }
}