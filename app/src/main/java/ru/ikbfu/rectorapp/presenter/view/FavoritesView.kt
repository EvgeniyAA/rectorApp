package ru.ikbfu.rectorapp.presenter.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.ikbfu.rectorapp.model.data.server.model.FavoriteData

@StateStrategyType(AddToEndSingleStrategy::class)
interface FavoritesView : MvpView {
    fun loadRecyclerData(data: List<FavoriteData>)
    fun showDownLoadProgress(isVisible: Boolean = false)
}