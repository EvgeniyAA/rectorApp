package ru.ikbfu.rectorapp.model.system

import java.util.concurrent.Executor

interface ExecutorsProvider {
    fun mainThreadExecutor(): Executor
    fun newSingleThreadExecutor(): Executor
}