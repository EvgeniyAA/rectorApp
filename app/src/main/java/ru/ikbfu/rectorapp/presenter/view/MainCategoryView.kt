package ru.ikbfu.rectorapp.presenter.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.ikbfu.rectorapp.model.data.server.model.SelectionCommitteeElement

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainCategoryView : MvpView {
    fun initTree(data: List<SelectionCommitteeElement>)
    fun setTreeData(data: List<SelectionCommitteeElement>)
    fun showDownLoadProgress(isVisible: Boolean = false)
}