package ru.ikbfu.rectorapp.ui.fragments.studentContingent

import com.jakewharton.rxbinding3.material.selections
import kotlinx.android.synthetic.main.student_contingent_fragment.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.ikbfu.rectorapp.R
import ru.ikbfu.rectorapp.model.interactor.studentContingent.StudentContingentInteractor
import ru.ikbfu.rectorapp.model.repository.studentContingent.StudentContingentRepository
import ru.ikbfu.rectorapp.presenter.studentContingent.StudentContingentPresenter
import ru.ikbfu.rectorapp.presenter.view.StudentContingentView
import ru.ikbfu.rectorapp.ui.fragments.base.MvpBaseFlowFragment
import ru.ikbfu.rectorapp.ui.fragments.base.MvpBaseFragment
import toothpick.Scope
import toothpick.config.Module

class StudentContingentFragment : MvpBaseFlowFragment(), StudentContingentView {
    override val layout: Int = R.layout.student_contingent_fragment
    companion object {
        fun newInstance() = StudentContingentFragment()
    }

    override val containerId: Int = R.id.students_container


    @InjectPresenter
    lateinit var presenter: StudentContingentPresenter

    @ProvidePresenter
    fun providePresenter(): StudentContingentPresenter = scope
        .getInstance(StudentContingentPresenter::class.java)

    override fun installModules(scope: Scope) {
        scope.installModules(object: Module(){
            init {
                bind(StudentContingentRepository::class.java).singleton()
                bind(StudentContingentInteractor::class.java).singleton()
            }
        })
        super.installModules(scope)
    }

    override fun showDownLoadProgress(isVisible: Boolean) {
        showProgressDialog(isVisible)
    }

    override fun tabSwitch() =
        tabs.selections().map {
            if (it.position == 0) StudentTab.CurrentStudentsTab
            else StudentTab.GraduetedStudentsTab
        }.subscribe {
            when (it) {
                is StudentTab.CurrentStudentsTab -> presenter.showCurrentStudents()
                is StudentTab.GraduetedStudentsTab -> presenter.showGraduetedStudents()
            }
        }.bind()

    override fun onBackPressed() = presenter.onBackPressed()
}