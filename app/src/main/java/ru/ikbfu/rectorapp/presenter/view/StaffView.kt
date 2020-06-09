package ru.ikbfu.rectorapp.presenter.view

import io.reactivex.Observable
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.ikbfu.rectorapp.ui.fragments.staff.StaffTab

@StateStrategyType(AddToEndSingleStrategy::class)
interface StaffView : MvpView {
    fun tabSwitch()
    fun showDownLoadProgress(isVisible: Boolean = false)
}