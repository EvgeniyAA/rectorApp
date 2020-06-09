package ru.ikbfu.rectorapp.model.data.mapper

import ru.ikbfu.rectorapp.model.data.db.entity.SelectionCommitteeEntity
import ru.ikbfu.rectorapp.model.data.server.model.*

class SelectionCommitteeElementMapper {

    fun toFavorite(selectionCommitteeEntity: SelectionCommitteeElement): FavoriteData {
        val path = selectionCommitteeEntity.name
        val maxValue = selectionCommitteeEntity.maxValue ?: selectionCommitteeEntity.value
        return FavoriteData(
            selectionCommitteeEntity.id,
            selectionCommitteeEntity.name,
            selectionCommitteeEntity.value,
            maxValue,
            null
        )
    }

    fun toFavorite(selectionCommitteeEntity: SelectionCommitteeEntity): FavoriteData {
        val path = selectionCommitteeEntity.name
        val maxValue = selectionCommitteeEntity.maxValue ?: selectionCommitteeEntity.value
        return FavoriteData(
            selectionCommitteeEntity.id,
            selectionCommitteeEntity.name,
            selectionCommitteeEntity.value,
            maxValue,
            null
        )
    }

    fun toFavorite(staffItem: StaffHierarchyItem): FavoriteData {
        val path = staffItem.name
        val maxValue = staffItem.maxValue ?: staffItem.value
        return FavoriteData(
            staffItem.id,
            staffItem.name,
            staffItem.value,
            maxValue,
            null
        )
    }

    fun toFavorite(graduateItem: Graduate): FavoriteData {
        val maxValue = graduateItem.firstValue + graduateItem.secondValue
        return FavoriteData(
            graduateItem.id,
            graduateItem.title,
            maxValue,
            maxValue,
            null
        )
    }

    fun toFavorite(student: Student): FavoriteData {
        val maxValue = student.maxValue ?: student.value
        return FavoriteData(
            student.id,
            student.name,
            student.value,
            maxValue,
            null
        )
    }

    fun toSelectionCommitteeEntity(item: SelectionCommitteeElement): SelectionCommitteeEntity =
        SelectionCommitteeEntity(
            item.id,
            item.name,
            item.value,
            item.maxValue,
            item.elementType,
            item.childIds,
            item.underlined,
            item.color,
            item.needToShow,
            item.isCollapsed,
            item.isFavorite
        )

    fun toSelectionCommitteeElement(item: SelectionCommitteeEntity): SelectionCommitteeElement {
        var elem = SelectionCommitteeElement(
            item.id,
            item.name,
            item.value,
            item.maxValue,
            item.elementType,
            item.childIds,
            item.underlined,
            item.color
        )
        elem.needToShow= item.needToShow
        elem.isCollapsed = item.isCollapsed
        elem.isFavorite = item.isFavorite
        return elem
    }

}