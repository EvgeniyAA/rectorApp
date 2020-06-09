package ru.ikbfu.rectorapp.model.data.server.model

import com.fasterxml.jackson.annotation.JsonValue

enum class UnderlineType constructor(var value: Int) {
    NONE(0),
    PART(1),
    FULL(2);

    @JsonValue
    override fun toString(): String {
        return value.toString()
    }
}