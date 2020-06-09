package ru.ikbfu.rectorapp.model.data.server.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Graduate(
    @JsonProperty("id")val id: String,
    @JsonProperty("title")val title: String,
    @JsonProperty("firstValue")val firstValue: Int,
    @JsonProperty("secondValue")val secondValue: Int
    ){
    var isFavorite: Boolean = false
}