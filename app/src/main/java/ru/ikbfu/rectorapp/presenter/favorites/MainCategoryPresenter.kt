package ru.ikbfu.rectorapp.presenter.favorites

import moxy.InjectViewState
import ru.ikbfu.rectorapp.Screens
import ru.ikbfu.rectorapp.global.BasePresenter
import ru.ikbfu.rectorapp.model.data.server.model.SelectionCommitteeElement
import ru.ikbfu.rectorapp.model.interactor.favorites.MainCategoryInteractor
import ru.ikbfu.rectorapp.presenter.view.MainCategoryView
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class MainCategoryPresenter @Inject constructor(
    private val interactor: MainCategoryInteractor,
    private val router: Router
) : BasePresenter<MainCategoryView>() {

    fun loadData() {
        interactor.loadData()
            .doOnSubscribe { viewState.showDownLoadProgress(true) }
            .doAfterTerminate { viewState.showDownLoadProgress() }
            .subscribe({
                viewState.initTree(it)
            }, {
                Timber.e(it)
            }).bind()
    }

    fun collapseChildsOf(item: SelectionCommitteeElement) =
        interactor.collapseChildsOf(item)
            .subscribe({
                viewState.setTreeData(it)
            }, {
                Timber.e(it)
            }).bind()


    fun checked(item: SelectionCommitteeElement) =
        interactor.checked(item)
            .subscribe({
                viewState.setTreeData(it)
            }, {
                Timber.e(it)
            }).bind()

    fun onBackPressed() = router.exit()

    fun goToAllCategories() = router.newRootScreen(Screens.GroupScreen)
}