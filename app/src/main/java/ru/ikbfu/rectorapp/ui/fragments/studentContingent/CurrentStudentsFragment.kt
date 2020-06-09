package ru.ikbfu.rectorapp.ui.fragments.studentContingent

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.current_students_fragment.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.ikbfu.rectorapp.R
import ru.ikbfu.rectorapp.model.data.server.model.Student
import ru.ikbfu.rectorapp.presenter.studentContingent.CurrentStudentsPresenter
import ru.ikbfu.rectorapp.presenter.view.CurrentStudentsView
import ru.ikbfu.rectorapp.ui.fragments.base.MvpBaseFragment
import ru.ikbfu.rectorapp.utils.delegate.CompositeDelegateAdapter
import ru.ikbfu.rectorapp.utils.delegate.checkedItems

class CurrentStudentsFragment : MvpBaseFragment(), CurrentStudentsView {
    override val layout: Int = R.layout.current_students_fragment

    companion object{
        fun newInstance() = CurrentStudentsFragment()
    }

    @InjectPresenter
    lateinit var presenter: CurrentStudentsPresenter

    @ProvidePresenter
    fun providePresenter(): CurrentStudentsPresenter = scope
        .getInstance(CurrentStudentsPresenter::class.java)

    private lateinit var treeAdapter: CurrentStudentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        presenter.initTree()
    }

    private fun initRecycler() {
        treeAdapter = CurrentStudentAdapter(
            data = mutableListOf(),
            editable = true,
            onItemSelected = { presenter.collapseChildsOf(it) },
            onItemChecked = { presenter.checked(it) })

        students_tree.adapter = treeAdapter
    }

    override fun showTree(data: List<Student>) = treeAdapter.replaceData(data)
}