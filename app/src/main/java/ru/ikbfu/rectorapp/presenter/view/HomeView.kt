package ru.ikbfu.rectorapp.presenter.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.ikbfu.rectorapp.model.data.server.model.SelectionCommitteeElement

@StateStrategyType(AddToEndSingleStrategy::class)
interface HomeView : MvpView {
    fun setAvatar(name: String, surname: String, url: String? = null)
    fun setUserName(name: String, surname: String)
    fun setUserDivision(division: String)
    fun setUserActivity(activity: String)
    fun initTree(data: List<SelectionCommitteeElement>)
    fun setTreeData(data: List<SelectionCommitteeElement>)
    fun showDownLoadProgress(isVisible: Boolean = false)
    fun showRefreshProgress(isVisible: Boolean = false)

    fun userProfileCollapsed(value: Boolean)
    fun setupAllAplicationsCount(value: Int)
}