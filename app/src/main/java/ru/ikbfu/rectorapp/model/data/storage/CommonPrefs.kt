package ru.ikbfu.rectorapp.model.data.storage

import ru.ikbfu.rectorapp.model.data.server.model.User

interface CommonPrefs {
    var user: User

    var token: String?
    var id: Int
    var email: String
    var userFirstName: String
    var userMiddleName: String
    var userLastName: String
    var userActivity: String
    var userDivision: String
    var userRole: String
    var isProfileCollapsed: Boolean


    val authorized: Boolean
    var firstAuthorized: Boolean

    fun clear()
}