package ru.ikbfu.rectorapp.model.system

sealed class NavigationAction {

    object Back : NavigationAction()

    object LogOut : NavigationAction() {
        object AdminNavigation : NavigationAction()
    }
}