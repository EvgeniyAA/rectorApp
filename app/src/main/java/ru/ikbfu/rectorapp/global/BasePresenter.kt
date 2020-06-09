package ru.ikbfu.rectorapp.global

import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SingleStateStrategy::class)
open class BasePresenter<V : MvpView> : MvpPresenter<V>(),
    CompositeDisposableComponent by CompositeDisposableComponentImpl() {

    override fun onDestroy() {
        clear()
        super.onDestroy()
    }
}