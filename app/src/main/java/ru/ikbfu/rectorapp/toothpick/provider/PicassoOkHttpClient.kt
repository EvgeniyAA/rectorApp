package ru.ikbfu.rectorapp.toothpick.provider

import android.content.Context
import android.util.Base64
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Cache
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import ru.ikbfu.rectorapp.BuildConfig
import ru.ikbfu.rectorapp.model.data.storage.CommonPrefs
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider


class PicassoOkHttpClient @Inject constructor(
    private val context: Context,
    private val prefs: CommonPrefs
) : Provider<OkHttpClient> {
    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
    }

    override fun get(): OkHttpClient = with(OkHttpClient.Builder()) {
        connectTimeout(3, TimeUnit.SECONDS)

        addInterceptor { chain ->

            val builder = chain.request().newBuilder()
            if (chain.request().header(AUTHORIZATION_HEADER) == null) {
                builder.addHeader(AUTHORIZATION_HEADER, prefs.token ?: "")
            }

            var response = chain.proceed(builder.build())
            try {
                val contentType: MediaType? = response.body!!.contentType()
                var base64String: String =
                    response.body!!.string()
                base64String = base64String.replace("data:image/jpeg;base64,", "")
                val decodedString: ByteArray = Base64.decode(base64String, Base64.DEFAULT)
                val body: ResponseBody = ResponseBody.create(contentType, decodedString)
                response = response.newBuilder().body(body).build()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            response
        }
        this.cache(Cache(File("${context.cacheDir.absolutePath}/picasso-cache/"), Int.MAX_VALUE.toLong()))
        //val pinner: CertificatePinner = CertificatePinner.Builder()
        //    .add(VALID_KEY.first, VALID_KEY.second)
        //    .build()
        //certificatePinner(pinner)
        addNetworkInterceptor(ChuckInterceptor(context))
        if (BuildConfig.DEBUG) {
            addNetworkInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.HEADERS })
        }
        build()
    }
}