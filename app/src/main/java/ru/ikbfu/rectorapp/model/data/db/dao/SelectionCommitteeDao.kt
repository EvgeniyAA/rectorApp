package ru.ikbfu.rectorapp.model.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single
import ru.ikbfu.rectorapp.model.data.db.entity.SelectionCommitteeEntity

@Dao
abstract class SelectionCommitteeDao : BaseDao<SelectionCommitteeEntity> {
    @Query("SELECT * FROM selectionCommittee")
    abstract fun getAll(): Single<List<SelectionCommitteeEntity>>

    @Query("DELETE FROM selectionCommittee")
    abstract fun deleteAll()
}