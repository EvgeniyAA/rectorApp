package ru.ikbfu.rectorapp.model.repository

import ru.ikbfu.rectorapp.model.data.storage.CommonPrefs
import javax.inject.Inject

class MainActivityRepository @Inject constructor(
    private val prefs: CommonPrefs
) {
    val authorized
        get() = prefs.authorized
}