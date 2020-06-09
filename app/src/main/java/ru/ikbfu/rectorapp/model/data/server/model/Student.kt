package ru.ikbfu.rectorapp.model.data.server.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Student(
    @JsonProperty("id") val id: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("value") val value: Int,
    @JsonProperty("maxValue") val maxValue: Int?,
    @JsonProperty("elementType") val elementType: ElementType,
    @JsonProperty("childIds") val childIds: List<String>? = null,
    @JsonProperty("underlined") val underlined: UnderlineType = UnderlineType.NONE,
    @JsonProperty("color") val color: String? = null

){
    var needToShow: Boolean = true
    var isCollapsed: Boolean = false
    var isFavorite: Boolean = false
}