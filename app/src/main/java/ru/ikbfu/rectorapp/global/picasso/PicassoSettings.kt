package ru.ikbfu.rectorapp.global.picasso

import android.content.Context
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import ru.ikbfu.rectorapp.BuildConfig
import timber.log.Timber
import java.util.concurrent.Executors

fun picassoAuthInstance(context: Context, httpClient: OkHttpClient) = with(Picasso.Builder(context)) {
    downloader(OkHttp3Downloader(httpClient))
        .listener { picasso, uri, exception -> Timber.e(exception) }
    executor(Executors.newFixedThreadPool(3))
    val picasso = this.build()
    if (BuildConfig.DEBUG) {
        picasso.setIndicatorsEnabled(true)
    }

    Picasso.setSingletonInstance(picasso)
}