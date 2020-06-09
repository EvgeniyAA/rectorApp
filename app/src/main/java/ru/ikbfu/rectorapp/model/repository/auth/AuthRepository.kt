package ru.ikbfu.rectorapp.model.repository.auth

import ru.ikbfu.rectorapp.model.data.db.dao.SelectionCommitteeDao
import ru.ikbfu.rectorapp.model.data.mapper.SelectionCommitteeElementMapper
import ru.ikbfu.rectorapp.model.data.server.ApiIKBFU
import ru.ikbfu.rectorapp.model.data.server.model.LoginRequest
import ru.ikbfu.rectorapp.model.data.server.model.LoginResponse
import ru.ikbfu.rectorapp.model.data.storage.CommonPrefs
import ru.ikbfu.rectorapp.model.system.SchedulersProvider
import timber.log.Timber
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiIKBFU: ApiIKBFU,
    private val prefs: CommonPrefs,
    private val mapper: SelectionCommitteeElementMapper,
    private val selectionCommitteeDao: SelectionCommitteeDao,
    private val schedulers: SchedulersProvider
) {
    fun login(email: String, password: String) = apiIKBFU.login(LoginRequest(email, password))
        .doOnSuccess {
            saveAuthInfo(it)
        }
        .ignoreElement()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())

    private fun saveAuthInfo(response: LoginResponse){
        prefs.token = "Bearer ${response.token}"
    }
    fun updateUserInfo() = apiIKBFU.getUserInfo()
        .doOnSuccess {
            prefs.user = it
        }
        .ignoreElement()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())



    fun uploadPriemka() = apiIKBFU.getPriemka()
        .flattenAsFlowable { it }
        .map { mapper.toSelectionCommitteeEntity(it) }
        .toList()
        .doOnSuccess {
            selectionCommitteeDao.deleteAll()
            selectionCommitteeDao.insertAll(it)  }
        .ignoreElement()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())


}