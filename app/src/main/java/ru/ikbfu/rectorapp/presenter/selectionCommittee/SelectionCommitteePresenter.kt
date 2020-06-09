package ru.ikbfu.rectorapp.presenter.selectionCommittee

import moxy.InjectViewState
import ru.ikbfu.rectorapp.global.BasePresenter
import ru.ikbfu.rectorapp.model.interactor.selectionCommittee.SelectionCommitteeInteractor
import ru.ikbfu.rectorapp.presenter.view.SelectionCommitteeView
import javax.inject.Inject

@InjectViewState
class SelectionCommitteePresenter @Inject constructor(
    private val interactor: SelectionCommitteeInteractor
) : BasePresenter<SelectionCommitteeView>() {

}