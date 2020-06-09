package ru.ikbfu.rectorapp.ui.fragments.selectionCommittee

import ru.ikbfu.rectorapp.R
import ru.ikbfu.rectorapp.presenter.view.SelectionCommitteeView
import ru.ikbfu.rectorapp.ui.fragments.base.MvpBaseFragment

class SelectionCommitteeFragment : MvpBaseFragment(), SelectionCommitteeView {
    override val layout: Int = R.layout.selection_committee_fragment
    companion object {
        fun newInstance() = SelectionCommitteeFragment()
    }
}