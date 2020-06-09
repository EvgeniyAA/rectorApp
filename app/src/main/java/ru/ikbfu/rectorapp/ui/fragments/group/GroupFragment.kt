package ru.ikbfu.rectorapp.ui.fragments.group

import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_group.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.ikbfu.rectorapp.R
import ru.ikbfu.rectorapp.extensions.subscribeBy
import ru.ikbfu.rectorapp.model.data.server.model.Category
import ru.ikbfu.rectorapp.model.data.server.model.DataType
import ru.ikbfu.rectorapp.model.system.FlowRouter
import ru.ikbfu.rectorapp.presenter.group.GroupPresenter
import ru.ikbfu.rectorapp.presenter.view.GroupView
import ru.ikbfu.rectorapp.toothpick.qualifier.InnerNavigation
import ru.ikbfu.rectorapp.ui.adapters.GroupAdapter
import ru.ikbfu.rectorapp.ui.fragments.base.MvpBaseFragment
import ru.ikbfu.rectorapp.utils.delegate.CompositeDelegateAdapter
import ru.terrakok.cicerone.Router
import timber.log.Timber
import toothpick.Scope
import toothpick.config.Module
import java.util.concurrent.TimeUnit

class GroupFragment : MvpBaseFragment(), GroupView {
    override val layout: Int = R.layout.fragment_group

    companion object {
        fun newInstance() = GroupFragment()
    }

    @InjectPresenter
    lateinit var presenter: GroupPresenter

    @ProvidePresenter
    fun providePresenter(): GroupPresenter = scope
        .getInstance(GroupPresenter::class.java)

    private lateinit var groupAdapter: CompositeDelegateAdapter<Category>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()

        RxTextView.textChanges(search_et)
            .skipInitialValue()
            .debounce(300L, TimeUnit.MILLISECONDS)
            .map { it.toString() }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy({ text ->
                presenter.filterCategories(text)
            }).bind()

        presenter.getData()
    }

    private fun initRecycler() {
        val recyclerAdapter = GroupAdapter { objectType ->
            when (objectType) {
                DataType.SELECTION_COMMITTEE -> presenter.openSelectionCommittee()
                DataType.STUDENT_CONTINGENT -> presenter.openStudentContingent()
                DataType.STAFF -> presenter.openStaff()
                DataType.SCIENCE -> presenter.openScience()
            }
        }
        groupAdapter = CompositeDelegateAdapter.Companion.Builder<Category>()
            .add(recyclerAdapter)
            .build()
        group_recycler.adapter = groupAdapter
    }

    override fun setData(data: List<Category>) {
        groupAdapter.replaceData(data)
    }
}

