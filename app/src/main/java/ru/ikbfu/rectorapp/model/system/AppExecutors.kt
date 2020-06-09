package ru.ikbfu.rectorapp.model.system

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors : ExecutorsProvider {
    override fun mainThreadExecutor(): Executor = MainThreadExecutor()

    override fun newSingleThreadExecutor(): Executor = Executors.newSingleThreadExecutor()
}