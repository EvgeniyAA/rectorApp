package ru.ikbfu.rectorapp.ui.fragments.staff

sealed class StaffTab {
    object ParametersTab: StaffTab()
    object HierarchyTab: StaffTab()
}