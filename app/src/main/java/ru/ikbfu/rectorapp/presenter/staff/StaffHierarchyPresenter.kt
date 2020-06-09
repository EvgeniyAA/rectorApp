package ru.ikbfu.rectorapp.presenter.staff

import moxy.InjectViewState
import ru.ikbfu.rectorapp.global.BasePresenter
import ru.ikbfu.rectorapp.model.data.server.model.StaffHierarchyItem
import ru.ikbfu.rectorapp.model.interactor.staff.StaffInteractor
import ru.ikbfu.rectorapp.presenter.view.StaffHierarchyView
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class StaffHierarchyPresenter @Inject constructor(
    private val interactor: StaffInteractor
) : BasePresenter<StaffHierarchyView>() {

    fun initTree() {
        val params = interactor.getParams().filter { !it.needToShow }.map { it.type }
        val staff = interactor.getHierarchy()
        if(!staff.isNullOrEmpty())
            viewState.showTree(staff)//.filter { !params.contains(it.type) })
    }

    fun collapseChildsOf(item: StaffHierarchyItem) =
        interactor.collapseChildsOf(item)
            .subscribe({
                viewState.showTree(it)
            },{
                Timber.e(it)
            }).bind()

    fun checked(item: StaffHierarchyItem) =
        interactor.checked(item)
            .subscribe({
                viewState.showTree(it)
            }, {
                Timber.e(it)
            }).bind()
}