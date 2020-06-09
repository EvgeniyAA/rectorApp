package ru.ikbfu.rectorapp.ui.fragments.studentContingent

sealed class StudentTab {
    object CurrentStudentsTab : StudentTab()
    object GraduetedStudentsTab : StudentTab()
}