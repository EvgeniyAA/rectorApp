package ru.ikbfu.rectorapp.model.system.message

data class SystemMessage(
    val text: String,
    val type: SystemMessageType = SystemMessageType.TOAST
)