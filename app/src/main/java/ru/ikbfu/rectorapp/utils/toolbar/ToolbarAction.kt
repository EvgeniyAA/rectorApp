package ru.ikbfu.rectorapp.utils.toolbar

import android.os.Parcel
import android.os.Parcelable

class ToolbarAction(`in`: Parcel) : Parcelable {
    val iconResId: Int
        get() = 0

    fun action() {}

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ToolbarAction> = object : Parcelable.Creator<ToolbarAction> {
            override fun createFromParcel(`in`: Parcel): ToolbarAction {
                return ToolbarAction(`in`)
            }

            override fun newArray(size: Int): Array<ToolbarAction?> {
                return arrayOfNulls(size)
            }
        }
    }
}