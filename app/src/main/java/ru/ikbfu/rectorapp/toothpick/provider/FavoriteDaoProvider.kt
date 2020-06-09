package ru.ikbfu.rectorapp.toothpick.provider

import ru.ikbfu.rectorapp.model.data.db.AppDatabase
import ru.ikbfu.rectorapp.model.data.db.dao.FavoritesDao
import javax.inject.Inject
import javax.inject.Provider

class FavoriteDaoProvider @Inject constructor(private val database: AppDatabase) :
    Provider<FavoritesDao> {
    override fun get(): FavoritesDao = database.favoritesDao()
}