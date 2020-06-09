package ru.ikbfu.rectorapp.model.system

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import javax.inject.Inject

class MainThreadExecutor @Inject constructor() : Executor {

    private val handler = Handler(Looper.getMainLooper())

    override fun execute(command: Runnable?) {
        handler.post(command)
    }
}