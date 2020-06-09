package ru.ikbfu.rectorapp.model.data.storage

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import ru.ikbfu.rectorapp.model.data.server.model.User
import javax.inject.Inject

class CommonPrefsProvider @Inject constructor(val context: Context) : CommonPrefs {
    companion object {
        private const val PREFS_NAME = "ru.ikbfu.rectorapp.auth_utils"
        private const val PREF_TOKEN_VALUE = "ru.ikbfu.rectorapp.auth_value"
        private const val PREF_EMAIL_VALUE = "ru.ikbfu.rectorapp.email_value"
        private const val PREF_IS_FIRST_AUTHORIZED_VALUE = "ru.ikbfu.rectorapp.is_first_authorized"
        private const val PREF_USER_ID = "ru.ikbfu.rectorapp.user_id_value"
        private const val PREF_USER_FIRST_NAME_VALUE = "ru.ikbfu.rectorapp.user_first_name_value"
        private const val PREF_USER_LAST_NAME_VALUE = "ru.ikbfu.rectorapp.user_last_name_value"
        private const val PREF_USER_MIDDLE_NAME_VALUE = "ru.ikbfu.rectorapp.user_middle_name_value"
        private const val PREF_USER_ACTIVITY_VALUE = "ru.ikbfu.rectorapp.user_activity_value"
        private const val PREF_USER_DIVISION_VALUE = "ru.ikbfu.rectorapp.user_division_value"
        private const val PREF_USER_ROLE_VALUE = "ru.ikbfu.rectorapp.user_role_value"
        private const val PREF_PROFILE_COLLAPSED = "ru.ikbfu.rectorapp.profile_collapsed"
    }

    private inline fun SharedPreferences.edit(block: SharedPreferences.Editor.() -> Unit) {
        edit().apply { block() }.apply()
    }

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(
            PREFS_NAME,
            Activity.MODE_PRIVATE
        )
    }

    override var id: Int
        get() = prefs.getInt(PREF_USER_ID, -1)
        set(value) {
            prefs.edit { putInt(PREF_USER_ID, value) }
        }

    override var userFirstName: String
        get() = prefs.getString(PREF_USER_FIRST_NAME_VALUE, "") ?: ""
        set(value) {
            prefs.edit { putString(PREF_USER_FIRST_NAME_VALUE, value) }
        }

    override var userMiddleName: String
        get() = prefs.getString(PREF_USER_MIDDLE_NAME_VALUE, "") ?: ""
        set(value) {
            prefs.edit { putString(PREF_USER_MIDDLE_NAME_VALUE, value) }
        }

    override var userLastName: String
        get() = prefs.getString(PREF_USER_LAST_NAME_VALUE, "") ?: ""
        set(value) {
            prefs.edit { putString(PREF_USER_LAST_NAME_VALUE, value) }
        }

    override var userActivity: String
        get() = prefs.getString(PREF_USER_ACTIVITY_VALUE, "") ?: ""
        set(value) {
            prefs.edit { putString(PREF_USER_ACTIVITY_VALUE, value) }
        }

    override var userDivision: String
        get() = prefs.getString(PREF_USER_DIVISION_VALUE, "") ?: ""
        set(value) {
            prefs.edit { putString(PREF_USER_DIVISION_VALUE, value) }
        }

    override var userRole: String
        get() = prefs.getString(PREF_USER_ROLE_VALUE, "") ?: ""
        set(value) {
            prefs.edit { putString(PREF_USER_ROLE_VALUE, value) }
        }

    override var token: String?
        get() = prefs.getString(PREF_TOKEN_VALUE, "") ?: ""
        set(value) {
            prefs.edit { putString(PREF_TOKEN_VALUE, value) }
        }
    override var email: String
        get() = prefs.getString(PREF_EMAIL_VALUE, "") ?: ""
        set(value) {
            prefs.edit { putString(PREF_EMAIL_VALUE, value) }
        }

    override var isProfileCollapsed: Boolean
        get() = prefs.getBoolean(PREF_PROFILE_COLLAPSED, false)
        set(value) {
            prefs.edit { putBoolean(PREF_PROFILE_COLLAPSED, value) }
        }

    override var user: User
        get() {
            val u = User(
                id,
                userFirstName,
                userMiddleName,
                userLastName,
                email,
                userDivision,
                userActivity
            )
            u.isProfileCollapsed = isProfileCollapsed
            return u
        }
        set(value) {
            id = value.id
            userFirstName = value.firstName ?: ""
            userMiddleName = value.middleName ?: ""
            userLastName = value.lastName ?: ""
            email = value.email ?: ""
            userDivision = value.division
            userActivity = value.activity ?: ""
            isProfileCollapsed = value.isProfileCollapsed
        }

    override val authorized: Boolean
        get() = !token.isNullOrEmpty()


    override var firstAuthorized: Boolean
        get() = prefs.getBoolean(PREF_IS_FIRST_AUTHORIZED_VALUE, true)
        set(value) {
            prefs.edit { putBoolean(PREF_IS_FIRST_AUTHORIZED_VALUE, value) }
        }

    override fun clear() = prefs.edit { clear() }
}