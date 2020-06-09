package ru.ikbfu.rectorapp.model.interactor.auth

import io.reactivex.Completable
import ru.ikbfu.rectorapp.model.repository.auth.AuthRepository
import javax.inject.Inject

class AuthInteractor @Inject constructor(private val repository: AuthRepository){
    fun login(email: String, password: String): Completable = repository.login(email, password).andThen(repository.updateUserInfo()).andThen(repository.uploadPriemka())
}