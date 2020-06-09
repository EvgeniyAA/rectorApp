package ru.ikbfu.rectorapp.model.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.ikbfu.rectorapp.model.data.server.model.ElementType
import ru.ikbfu.rectorapp.model.data.server.model.UnderlineType

class Converters {

    private val gson by lazy { Gson() }

    private inline fun <reified T> getAs(json: String): T =
        gson.fromJson(json, T::class.java)

    fun <T> convertToJson(obj: T): String =
        gson.toJson(obj)

    @TypeConverter
    fun underlineTypeFromString(jsonString: String): UnderlineType {
        val type = object : TypeToken<UnderlineType>() {}.type
        return gson.fromJson(jsonString, type)
    }

    @TypeConverter
    fun underlineTypeToString(underlineType: UnderlineType): String = convertToJson(underlineType)

    @TypeConverter
    fun elementTypeFromString(jsonString: String): ElementType {
        val type = object : TypeToken<ElementType>() {}.type
        return gson.fromJson(jsonString, type)
    }

    @TypeConverter
    fun elementTypeToString(elementType: ElementType?): String = convertToJson(elementType)

    @TypeConverter
    fun childIdsListFromString(jsonString: String): List<String> = getAs(jsonString)

    @TypeConverter
    fun childIdsListToString(childIds: List<String>?): String = convertToJson(childIds)
}