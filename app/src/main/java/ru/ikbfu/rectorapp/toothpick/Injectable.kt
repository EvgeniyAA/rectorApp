package ru.ikbfu.rectorapp.toothpick

import toothpick.config.Module

interface Injectable {
    fun getScopes(): Array<String>
    fun getModules(): Array<Module>? = null
}