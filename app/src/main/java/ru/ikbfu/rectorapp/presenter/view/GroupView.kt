package ru.ikbfu.rectorapp.presenter.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.ikbfu.rectorapp.model.data.server.model.Category

@StateStrategyType(AddToEndSingleStrategy::class)
interface GroupView : MvpView {
    fun setData(data: List<Category>)
}