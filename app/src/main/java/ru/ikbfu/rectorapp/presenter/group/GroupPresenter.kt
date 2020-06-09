package ru.ikbfu.rectorapp.presenter.group

import moxy.InjectViewState
import ru.ikbfu.rectorapp.Screens
import ru.ikbfu.rectorapp.global.BasePresenter
import ru.ikbfu.rectorapp.model.interactor.group.GroupInteractor
import ru.ikbfu.rectorapp.model.system.FlowRouter
import ru.ikbfu.rectorapp.model.system.NavigationAction
import ru.ikbfu.rectorapp.model.system.NavigationActionRelay
import ru.ikbfu.rectorapp.presenter.view.GroupView
import ru.ikbfu.rectorapp.toothpick.qualifier.groupNavigation
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class GroupPresenter @Inject constructor(
    private val interactor: GroupInteractor,
    private val router: Router,
    @groupNavigation
    private val actionNavigation: NavigationActionRelay
    ) : BasePresenter<GroupView>() {

    override fun onFirstViewAttach() {
        actionNavigation.get().subscribe {
            when (it) {
                NavigationAction.Back -> router.exit()
                else -> {
                }
            }
        }.bind()
    }
    fun getData() = interactor.getData()
        .subscribe({
            viewState.setData(it)
        }, {
            Timber.e(it)
        }).bind()

    fun filterCategories(text: String) =
        interactor.filterCategories(text)
            .subscribe({
                viewState.setData(it)
            },
                {
                    Timber.e(it)
                }).bind()

    fun openSelectionCommittee() = router.navigateTo(Screens.MainCategoryScreen)
    fun openStudentContingent() = router.navigateTo(Screens.StudentContingentScreen)
    fun openStaff() = router.navigateTo(Screens.StaffScreen)
    fun openScience() = router.navigateTo(Screens.ScienceScreen)
}