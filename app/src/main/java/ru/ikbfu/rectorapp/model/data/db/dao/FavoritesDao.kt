package ru.ikbfu.rectorapp.model.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single
import ru.ikbfu.rectorapp.model.data.db.entity.FavoriteEntity

@Dao
abstract class FavoritesDao : BaseDao<FavoriteEntity> {
    @Query("SELECT id FROM favorites")
    abstract fun getAll(): Single<List<FavoriteEntity>>
}