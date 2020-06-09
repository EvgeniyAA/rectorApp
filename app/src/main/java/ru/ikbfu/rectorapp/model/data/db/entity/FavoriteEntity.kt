package ru.ikbfu.rectorapp.model.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.ikbfu.rectorapp.model.data.db.Converters

@Entity(tableName = "favorites")
@TypeConverters(Converters::class)
data class FavoriteEntity (
    @PrimaryKey
    val id: String
)