package ru.ikbfu.rectorapp.presenter.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.ikbfu.rectorapp.model.data.server.model.Student

@StateStrategyType(AddToEndSingleStrategy::class)
interface CurrentStudentsView : MvpView {
    fun showTree(data: List<Student>)
}