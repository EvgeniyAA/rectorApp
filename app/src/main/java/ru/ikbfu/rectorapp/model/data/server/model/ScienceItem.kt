package ru.ikbfu.rectorapp.model.data.server.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ScienceItem(
    @JsonProperty("title")val title: String,
    @JsonProperty("desc")val desc: String,
    @JsonProperty("chartType")val chartType: ChartType,
    @JsonProperty("pieData")val pieData: List<PieEntity>? = null,
    @JsonProperty("barEntity")val barData: List<BarEntity>? = null
)