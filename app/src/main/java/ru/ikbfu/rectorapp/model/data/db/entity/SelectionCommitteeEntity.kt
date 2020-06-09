package ru.ikbfu.rectorapp.model.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.ikbfu.rectorapp.model.data.db.Converters
import ru.ikbfu.rectorapp.model.data.server.model.ElementType
import ru.ikbfu.rectorapp.model.data.server.model.UnderlineType

@Entity(tableName = "selectionCommittee")
@TypeConverters(Converters::class)
data class SelectionCommitteeEntity (
    @PrimaryKey
    val id: String,
    val name: String,
    val value: Int,
    val maxValue: Int?,
    val elementType: ElementType,
    val childIds: List<String>?,
    val underlined: UnderlineType,
    val color: String?,
    var needToShow: Boolean,
    var isCollapsed: Boolean,
    var isFavorite: Boolean
)