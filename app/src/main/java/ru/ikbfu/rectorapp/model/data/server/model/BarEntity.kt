package ru.ikbfu.rectorapp.model.data.server.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class BarEntity(
    @JsonProperty("dataType")val dataType: String,
    @JsonProperty("color")val color: String,
    @JsonProperty("barData")val barData: List<YearValue>
)