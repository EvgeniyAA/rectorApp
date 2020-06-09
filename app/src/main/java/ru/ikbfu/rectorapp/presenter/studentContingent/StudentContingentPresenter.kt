package ru.ikbfu.rectorapp.presenter.studentContingent

import moxy.InjectViewState
import ru.ikbfu.rectorapp.Screens
import ru.ikbfu.rectorapp.global.BasePresenter
import ru.ikbfu.rectorapp.model.interactor.studentContingent.StudentContingentInteractor
import ru.ikbfu.rectorapp.model.system.NavigationAction
import ru.ikbfu.rectorapp.model.system.NavigationActionRelay
import ru.ikbfu.rectorapp.presenter.view.StudentContingentView
import ru.ikbfu.rectorapp.toothpick.qualifier.groupNavigation
import ru.ikbfu.rectorapp.toothpick.qualifier.studentNavigation
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class StudentContingentPresenter @Inject constructor(
    private val interactor: StudentContingentInteractor,
    private val router: Router,
    @groupNavigation
    private val actionNavigation: NavigationActionRelay
) : BasePresenter<StudentContingentView>() {
    override fun onFirstViewAttach() {
        interactor.getAll()
            .doOnSubscribe { viewState.showDownLoadProgress(true) }
            .doAfterTerminate { viewState.showDownLoadProgress() }
            .subscribe({
                viewState.tabSwitch()
            },{
                Timber.e(it)
            }).bind()
    }

    fun showCurrentStudents() = router.navigateTo(Screens.CurrentStudentsScreen)
    fun showGraduetedStudents() = router.navigateTo(Screens.GraduetedStudentScreen)
    fun onBackPressed() = actionNavigation.pushAction(NavigationAction.Back)
}