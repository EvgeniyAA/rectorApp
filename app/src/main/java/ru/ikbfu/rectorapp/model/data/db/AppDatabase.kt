package ru.ikbfu.rectorapp.model.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.ikbfu.rectorapp.model.data.db.dao.FavoritesDao
import ru.ikbfu.rectorapp.model.data.db.dao.SelectionCommitteeDao
import ru.ikbfu.rectorapp.model.data.db.entity.FavoriteEntity
import ru.ikbfu.rectorapp.model.data.db.entity.SelectionCommitteeEntity

@Database(
    entities = [
        FavoriteEntity::class, SelectionCommitteeEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
    abstract fun selectionCommittee(): SelectionCommitteeDao
}