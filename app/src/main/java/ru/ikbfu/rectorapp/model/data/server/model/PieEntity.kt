package ru.ikbfu.rectorapp.model.data.server.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class PieEntity(
    @JsonProperty("value")val value: Float,
    @JsonProperty("label")val label: String,
    @JsonProperty("color")val color: String)