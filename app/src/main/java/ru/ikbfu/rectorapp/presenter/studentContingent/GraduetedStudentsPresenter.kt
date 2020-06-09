package ru.ikbfu.rectorapp.presenter.studentContingent

import moxy.InjectViewState
import ru.ikbfu.rectorapp.global.BasePresenter
import ru.ikbfu.rectorapp.model.data.server.model.Graduate
import ru.ikbfu.rectorapp.model.interactor.studentContingent.StudentContingentInteractor
import ru.ikbfu.rectorapp.presenter.view.GraduetedStudentsView
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class GraduetedStudentsPresenter @Inject constructor(
    private val interactor: StudentContingentInteractor
) : BasePresenter<GraduetedStudentsView>() {

    fun getAllData() {
        val graduates = interactor.getGraduates()
        if (!graduates.isNullOrEmpty())
            viewState.setupGraduatedData(graduates)
        val firstYearValue = graduates.sumBy { it.firstValue }
        val secondYearValue = graduates.sumBy { it.secondValue }
        val years = interactor.getGraduateYears()
        viewState.setFirstYear(years.first)
        viewState.setSecondYear(years.second)
        viewState.setFirstYearTotalValue(firstYearValue)
        viewState.setSecondYearTotalValue(secondYearValue)
        viewState.setTotalValue(firstYearValue+secondYearValue)
    }

    fun checked(item: Graduate) = interactor.checkedGraduate(item)
        .subscribe({

        }, {
            Timber.e(it)
        }).bind()
}