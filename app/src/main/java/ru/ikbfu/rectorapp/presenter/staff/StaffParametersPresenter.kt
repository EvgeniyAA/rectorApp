package ru.ikbfu.rectorapp.presenter.staff

import moxy.InjectViewState
import ru.ikbfu.rectorapp.global.BasePresenter
import ru.ikbfu.rectorapp.model.data.server.model.ShowParam
import ru.ikbfu.rectorapp.model.data.server.model.StaffType
import ru.ikbfu.rectorapp.model.interactor.staff.StaffInteractor
import ru.ikbfu.rectorapp.presenter.view.StaffParametersView
import javax.inject.Inject

@InjectViewState
class StaffParametersPresenter @Inject constructor(
    private val interactor: StaffInteractor
) : BasePresenter<StaffParametersView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setupParams(interactor.getParams())
    }

    fun itemPressed(item: ShowParam) {
        val params = interactor.getParams()
        params.map {
            if (it.id == item.id)
                it.needToShow = item.needToShow
        }
        if (!params.filter { it.type != StaffType.ALL }.all { it.needToShow } && item.type != StaffType.ALL)
            params.map {
                if (it.type == StaffType.ALL)
                    it.needToShow = false
            }
        else if (item.type == StaffType.ALL && item.needToShow)
            params.map { it.needToShow = true }
        viewState.setupParams(params)
    }
}
