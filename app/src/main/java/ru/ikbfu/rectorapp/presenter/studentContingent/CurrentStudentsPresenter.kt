package ru.ikbfu.rectorapp.presenter.studentContingent

import moxy.InjectViewState
import ru.ikbfu.rectorapp.global.BasePresenter
import ru.ikbfu.rectorapp.model.data.server.model.Student
import ru.ikbfu.rectorapp.model.interactor.studentContingent.StudentContingentInteractor
import ru.ikbfu.rectorapp.presenter.view.CurrentStudentsView
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class CurrentStudentsPresenter @Inject constructor(
    private val interactor: StudentContingentInteractor
) : BasePresenter<CurrentStudentsView>() {

    fun initTree() {
        val students = interactor.getCurrentStudents()
        if (!students.isNullOrEmpty())
            viewState.showTree(students)
    }

    fun collapseChildsOf(item: Student) =
        interactor.collapseChildsOf(item)
            .subscribe({
                viewState.showTree(it)
            }, {
                Timber.e(it)
            }).bind()

    fun checked(item: Student) = interactor.checked(item)
        .subscribe({
            viewState.showTree(it)
        }, {
            Timber.e(it)
        }).bind()
}