package ru.ikbfu.rectorapp.model.data.server.model

import com.fasterxml.jackson.annotation.JsonValue

enum class StaffType(private val value: String) {
    ALL("all"),
    SCIENCE_DOCTOR("scienceDoctor"),
    SCIENCE_KANDIDAT("scienceKandidat"),
    PROFESSOR("professor"),
    DOCENT("docent");

    @JsonValue
    override fun toString(): String = value

}