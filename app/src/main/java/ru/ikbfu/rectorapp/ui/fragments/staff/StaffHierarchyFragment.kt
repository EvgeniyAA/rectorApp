package ru.ikbfu.rectorapp.ui.fragments.staff

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.staff_hierarchy_fragment.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.ikbfu.rectorapp.R
import ru.ikbfu.rectorapp.model.data.server.model.StaffHierarchyItem
import ru.ikbfu.rectorapp.presenter.staff.StaffHierarchyPresenter
import ru.ikbfu.rectorapp.presenter.view.StaffHierarchyView
import ru.ikbfu.rectorapp.ui.fragments.base.MvpBaseFragment
import ru.ikbfu.rectorapp.utils.delegate.CompositeDelegateAdapter
import ru.ikbfu.rectorapp.utils.delegate.checkedItems

class StaffHierarchyFragment : MvpBaseFragment(), StaffHierarchyView {
    override val layout: Int = R.layout.staff_hierarchy_fragment

    companion object {
        fun newInstance() = StaffHierarchyFragment()
    }

    @InjectPresenter
    lateinit var presenter: StaffHierarchyPresenter

    @ProvidePresenter
    fun providePresenter(): StaffHierarchyPresenter = scope
        .getInstance(StaffHierarchyPresenter::class.java)

    private lateinit var treeAdapter: StaffTreeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        presenter.initTree()
    }

    private fun initRecycler() {
        treeAdapter = StaffTreeAdapter(
            data = mutableListOf(),
            editable = true,
            onItemSelected = { presenter.collapseChildsOf(it) },
            onItemChecked = { presenter.checked(it) })

        staff_tree.adapter = treeAdapter
    }

    override fun showTree(data: List<StaffHierarchyItem>) = treeAdapter.replaceData(data)
}