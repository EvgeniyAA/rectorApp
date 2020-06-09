package ru.ikbfu.rectorapp.model.data.server.model

import com.fasterxml.jackson.annotation.JsonValue

enum class ElementType constructor(var value: Int) {
    CHILD_DATA(0),
    ROOT_DATA(1),
    PARENT_DATA(2),
    AFTER_PARENT_DATA(3);

    @JsonValue
    override fun toString(): String {
        return value.toString()
    }
}