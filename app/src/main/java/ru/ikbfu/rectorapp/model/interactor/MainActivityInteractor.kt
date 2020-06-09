package ru.ikbfu.rectorapp.model.interactor

import ru.ikbfu.rectorapp.model.repository.MainActivityRepository
import javax.inject.Inject

class MainActivityInteractor @Inject constructor(private val repository: MainActivityRepository) {
    val authorized
        get() = repository.authorized
}