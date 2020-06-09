package ru.ikbfu.rectorapp.presenter.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface StudentContingentView : MvpView {
    fun tabSwitch()
    fun showDownLoadProgress(isVisible: Boolean = false)
}