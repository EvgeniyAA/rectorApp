package ru.ikbfu.rectorapp.ui.fragments.studentContingent

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.gradueted_students_fragment.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.ikbfu.rectorapp.R
import ru.ikbfu.rectorapp.model.data.server.model.Graduate
import ru.ikbfu.rectorapp.presenter.studentContingent.GraduetedStudentsPresenter
import ru.ikbfu.rectorapp.presenter.view.GraduetedStudentsView
import ru.ikbfu.rectorapp.ui.fragments.base.MvpBaseFragment
import ru.ikbfu.rectorapp.utils.delegate.CompositeDelegateAdapter
import ru.ikbfu.rectorapp.utils.delegate.checkedItems

class GraduetedStudentsFragment : MvpBaseFragment(), GraduetedStudentsView {
    override val layout: Int = R.layout.gradueted_students_fragment

    companion object {
        fun newInstance() = GraduetedStudentsFragment()
    }

    @InjectPresenter
    lateinit var presenter: GraduetedStudentsPresenter

    @ProvidePresenter
    fun providePresenter(): GraduetedStudentsPresenter = scope
        .getInstance(GraduetedStudentsPresenter::class.java)

    private lateinit var studentsAdapter: CompositeDelegateAdapter<Graduate>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        presenter.getAllData()
    }

    override fun setFirstYear(year: Int) {
        first_year.text = year.toString()
    }

    override fun setSecondYear(year: Int) {
        second_year.text = year.toString()
    }

    override fun setFirstYearTotalValue(value: Int) {
        first_year_total.text = value.toString()
    }

    override fun setSecondYearTotalValue(value: Int) {
        second_year_total.text = value.toString()
    }

    override fun setTotalValue(value: Int) {
        total_from_years.text = value.toString()
    }

    private fun initRecycler() {
        studentsAdapter = CompositeDelegateAdapter.Companion.Builder<Graduate>()
            .add(GraduateAdapter(true, compositeDisposable))
            .build()
        students.adapter = studentsAdapter
        studentsAdapter.actions.checkedItems().subscribe {
            presenter.checked(it)
        }.bind()
    }

    override fun setupGraduatedData(data: List<Graduate>) {
        studentsAdapter.replaceData(data)
    }
}