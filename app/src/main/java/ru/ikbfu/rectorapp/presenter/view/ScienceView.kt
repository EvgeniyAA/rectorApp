package ru.ikbfu.rectorapp.presenter.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.ikbfu.rectorapp.model.data.server.model.ScienceItem

@StateStrategyType(AddToEndSingleStrategy::class)
interface ScienceView : MvpView {
    fun setupData(data: List<ScienceItem>)
    fun showDownLoadProgress(isVisible: Boolean = false)
}