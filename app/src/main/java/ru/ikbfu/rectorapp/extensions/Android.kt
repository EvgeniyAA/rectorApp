package ru.ikbfu.rectorapp.extensions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Parcelable
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityCompat

private const val REQUEST_EXTERNAL_STORAGE = 1
private val PERMISSIONS_STORAGE = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
fun Activity.verifyStoragePermissions() {
    val permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    if (permission != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(
            this,
            PERMISSIONS_STORAGE,
            REQUEST_EXTERNAL_STORAGE
        )
    }
}

var View.visible: Boolean
    set(value) {
        if (value) this.visibility = View.VISIBLE
        else this.visibility = View.GONE
    }
    get() = this.visibility == View.VISIBLE

fun Any.objectScopeName() = "${javaClass.simpleName}_${hashCode()}"
