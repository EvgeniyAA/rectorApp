package ru.ikbfu.rectorapp.presenter.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.ikbfu.rectorapp.model.data.server.model.Graduate

@StateStrategyType(AddToEndSingleStrategy::class)
interface GraduetedStudentsView : MvpView {
    fun setFirstYear(year: Int)
    fun setSecondYear(year: Int)
    fun setFirstYearTotalValue(value: Int)
    fun setSecondYearTotalValue(value: Int)
    fun setTotalValue(value: Int)
    fun setupGraduatedData(data: List<Graduate>)
}