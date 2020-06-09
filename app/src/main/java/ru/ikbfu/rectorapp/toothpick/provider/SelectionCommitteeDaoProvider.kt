package ru.ikbfu.rectorapp.toothpick.provider

import ru.ikbfu.rectorapp.model.data.db.AppDatabase
import ru.ikbfu.rectorapp.model.data.db.dao.SelectionCommitteeDao
import javax.inject.Inject
import javax.inject.Provider

class SelectionCommitteeDaoProvider @Inject constructor(private val database: AppDatabase) :
    Provider<SelectionCommitteeDao> {
    override fun get(): SelectionCommitteeDao = database.selectionCommittee()
}