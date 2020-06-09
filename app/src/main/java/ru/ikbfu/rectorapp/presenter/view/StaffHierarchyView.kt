package ru.ikbfu.rectorapp.presenter.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.ikbfu.rectorapp.model.data.server.model.StaffHierarchyItem

@StateStrategyType(AddToEndSingleStrategy::class)
interface StaffHierarchyView : MvpView {
    fun showTree(data: List<StaffHierarchyItem>)
}