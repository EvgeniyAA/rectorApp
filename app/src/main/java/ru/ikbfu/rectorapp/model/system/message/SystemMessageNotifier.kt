package ru.ikbfu.rectorapp.model.system.message

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable

class SystemMessageNotifier {
    private val notifierRelay = PublishRelay.create<SystemMessage>()

    val notifier: Observable<SystemMessage> = notifierRelay.hide()

    fun sendSystemMessage(message: SystemMessage) = notifierRelay.accept(message)
    fun send(message: String) = notifierRelay.accept(SystemMessage(message))
}