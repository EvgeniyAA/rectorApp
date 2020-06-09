package ru.ikbfu.rectorapp.model.data.server.model

import com.fasterxml.jackson.annotation.JsonValue

enum class ChartType(private val value: String) {
    PIE("pie_chart"),
    BAR("bar_chart");

    @JsonValue
    override fun toString(): String = value

}