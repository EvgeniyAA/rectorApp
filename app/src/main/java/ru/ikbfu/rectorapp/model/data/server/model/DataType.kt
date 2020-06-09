package ru.ikbfu.rectorapp.model.data.server.model

import com.fasterxml.jackson.annotation.JsonValue

enum class DataType(private val value: String) {
    SELECTION_COMMITTEE("selection_committee"),
    STUDENT_CONTINGENT("student_contingent"),
    STAFF("staff"),
    SCIENCE("science");

    @JsonValue
    override fun toString(): String = value

}