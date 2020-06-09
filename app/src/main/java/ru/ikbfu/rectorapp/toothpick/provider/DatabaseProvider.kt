package ru.ikbfu.rectorapp.toothpick.provider

import android.content.Context
import androidx.room.Room
import ru.ikbfu.rectorapp.model.data.db.AppDatabase
import javax.inject.Inject
import javax.inject.Provider

class DatabaseProvider @Inject constructor(val context: Context) : Provider<AppDatabase> {
    override fun get(): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "database").build()
}