package ru.ikbfu.rectorapp.presenter.view

import moxy.MvpView
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface FragmentView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun showErrorToast(message: String)
}