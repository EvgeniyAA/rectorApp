package ru.ikbfu.rectorapp.model.data.server.model

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginRequest(
    @get:JsonProperty("login") val username: String,
    @get:JsonProperty("password") val password: String
)