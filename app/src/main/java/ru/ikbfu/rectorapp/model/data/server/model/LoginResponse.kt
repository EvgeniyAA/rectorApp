package ru.ikbfu.rectorapp.model.data.server.model

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginResponse (
    @JsonProperty("token") val token: String,
    @JsonProperty("login") val login: String
)