package ru.ikbfu.rectorapp.model.data.server.model

import com.fasterxml.jackson.annotation.JsonProperty
import ru.ikbfu.rectorapp.BuildConfig

data class User(
    @JsonProperty("id") var id: Int,
    @JsonProperty("firstName") var firstName: String?,
    @JsonProperty("middleName") var middleName: String?,
    @JsonProperty("lastName") var lastName: String?,
    @JsonProperty("email") var email: String?,
    @JsonProperty("division") var division: String,
    @JsonProperty("activity") var activity: String?
) {
    val fullName: String
        get() = "$lastName $firstName $middleName"
    var isProfileCollapsed: Boolean = false
    val avatarUrl: String = "${BuildConfig.ServerEndpoint}/api/user/$id/get_photo"

    override fun toString(): String {
        return "User{" +
                "email='" + email + '\''.toString() +
                ", first_name='" + firstName + '\''.toString() +
                ", middle_name='" + middleName + '\''.toString() +
                ", last_name='" + lastName + '\''.toString() +
                ", email=" + email +
                ", activity='" + activity + '\''.toString() +
                ", division='" + division + '\''.toString() +
                '}'.toString()
    }
}