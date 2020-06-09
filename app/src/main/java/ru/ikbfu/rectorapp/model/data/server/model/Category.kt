package ru.ikbfu.rectorapp.model.data.server.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("Title") val title: String,
    @SerializedName("Type") val type: DataType
)