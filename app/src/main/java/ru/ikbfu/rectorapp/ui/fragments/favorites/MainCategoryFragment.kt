package ru.ikbfu.rectorapp.ui.fragments.favorites

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.main_category_fragment.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.ikbfu.rectorapp.R
import ru.ikbfu.rectorapp.model.data.server.model.SelectionCommitteeElement
import ru.ikbfu.rectorapp.presenter.favorites.MainCategoryPresenter
import ru.ikbfu.rectorapp.presenter.view.MainCategoryView
import ru.ikbfu.rectorapp.ui.adapters.TreeAdapter
import ru.ikbfu.rectorapp.ui.fragments.base.MvpBaseFragment

class MainCategoryFragment : MvpBaseFragment(), MainCategoryView {
    override val layout: Int
        get() = R.layout.main_category_fragment

    companion object {
        fun newInstance() = MainCategoryFragment()
    }

    @InjectPresenter
    lateinit var presenter: MainCategoryPresenter

    @ProvidePresenter
    fun providePresenter(): MainCategoryPresenter = scope
        .getInstance(MainCategoryPresenter::class.java)

    private lateinit var treeAdapter: TreeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecycler()
        other_categories.setOnClickListener { presenter.goToAllCategories() }
        presenter.loadData()
    }

    override fun initTree(data: List<SelectionCommitteeElement>) {
        setTreeData(data)
    }

    override fun showDownLoadProgress(isVisible: Boolean) {
        showProgressDialog(isVisible)
    }

    private fun initRecycler() {
        treeAdapter =  TreeAdapter(
                    data = mutableListOf(),
                    editable = true,
                    onItemSelected = { presenter.collapseChildsOf(it) },
                    onItemChecked = { presenter.checked(it) })
        tree_recycler.adapter = treeAdapter
    }

    override fun setTreeData(data: List<SelectionCommitteeElement>) = treeAdapter.replaceData(data)


    override fun onBackPressed() {
        super.onBackPressed()
        presenter.onBackPressed()
    }
}