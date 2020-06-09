package ru.ikbfu.rectorapp.presenter.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.ikbfu.rectorapp.model.data.server.model.ShowParam

@StateStrategyType(AddToEndSingleStrategy::class)
interface StaffParametersView : MvpView {
    fun setupParams(items:  List<ShowParam>)
}