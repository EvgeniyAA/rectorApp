package ru.ikbfu.rectorapp

import androidx.fragment.app.Fragment
import ru.ikbfu.rectorapp.ui.fragments.RootFlowFragment
import ru.ikbfu.rectorapp.ui.fragments.auth.AuthFragment
import ru.ikbfu.rectorapp.ui.fragments.favorites.FavoritesFlowFragment
import ru.ikbfu.rectorapp.ui.fragments.favorites.FavoritesFragment
import ru.ikbfu.rectorapp.ui.fragments.favorites.MainCategoryFragment
import ru.ikbfu.rectorapp.ui.fragments.group.GroupFragment
import ru.ikbfu.rectorapp.ui.fragments.home.HomeFragment
import ru.ikbfu.rectorapp.ui.fragments.science.ScienceFragment
import ru.ikbfu.rectorapp.ui.fragments.selectionCommittee.SelectionCommitteeFragment
import ru.ikbfu.rectorapp.ui.fragments.staff.StaffFragment
import ru.ikbfu.rectorapp.ui.fragments.staff.StaffHierarchyFragment
import ru.ikbfu.rectorapp.ui.fragments.staff.StaffParametersFragment
import ru.ikbfu.rectorapp.ui.fragments.studentContingent.CurrentStudentsFragment
import ru.ikbfu.rectorapp.ui.fragments.studentContingent.GraduetedStudentsFragment
import ru.ikbfu.rectorapp.ui.fragments.studentContingent.StudentContingentFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    object AuthScreen : SupportAppScreen(){
        override fun getFragment(): Fragment = AuthFragment.newInstance()
    }

    object HomeScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = HomeFragment.newInstance()
    }

    object GroupScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = GroupFragment.newInstance()
    }

    object SelectionCommitteeScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = SelectionCommitteeFragment.newInstance()
    }

    object StudentContingentScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = StudentContingentFragment.newInstance()
    }

    object CurrentStudentsScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = CurrentStudentsFragment.newInstance()
    }

    object GraduetedStudentScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = GraduetedStudentsFragment.newInstance()
    }

    object StaffScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = StaffFragment.newInstance()
    }

    object StaffParametersScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = StaffParametersFragment.newInstance()
    }

    object StaffHierarchyScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = StaffHierarchyFragment.newInstance()
    }

    object ScienceScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = ScienceFragment.newInstance()
    }

    object FlowRootScreen: SupportAppScreen(){
        override fun getFragment(): Fragment = RootFlowFragment()
    }

    object FavoritesFlowScreen: SupportAppScreen(){
        override fun getFragment(): Fragment = FavoritesFlowFragment()
    }

    object FavoritesScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = FavoritesFragment.newInstance()
    }

    object MainCategoryScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = MainCategoryFragment.newInstance()
    }
}
