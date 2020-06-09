package ru.ikbfu.rectorapp.ui.fragments.science

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.science_fragment.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.ikbfu.rectorapp.R
import ru.ikbfu.rectorapp.extensions.subscribeBy
import ru.ikbfu.rectorapp.model.data.server.model.ChartType
import ru.ikbfu.rectorapp.model.data.server.model.ScienceItem
import ru.ikbfu.rectorapp.presenter.science.SciencePresenter
import ru.ikbfu.rectorapp.presenter.view.ScienceView
import ru.ikbfu.rectorapp.ui.fragments.base.MvpBaseFragment
import ru.ikbfu.rectorapp.utils.delegate.CompositeDelegateAdapter
import java.util.concurrent.TimeUnit

class ScienceFragment : MvpBaseFragment(), ScienceView {
    override val layout: Int = R.layout.science_fragment

    companion object {
        fun newInstance() = ScienceFragment()
    }

    @InjectPresenter
    lateinit var presenter: SciencePresenter

    @ProvidePresenter
    fun providePresenter(): SciencePresenter = scope
        .getInstance(SciencePresenter::class.java)

    private lateinit var recyclerAdapter: CompositeDelegateAdapter<ScienceItem>

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
                presenter.filterScienceData(text)
            }).bind()
        presenter.getScienceData()
    }

    private fun initRecycler() {
        recyclerAdapter = CompositeDelegateAdapter.Companion.Builder<ScienceItem>()
            .add(ScienceAdapter())
            .build()
        charts_recycler.adapter = recyclerAdapter
    }

    override fun showDownLoadProgress(isVisible: Boolean) {
        showProgressDialog(isVisible)
    }

    override fun onBackPressed() = presenter.onBackPressed()

    override fun setupData(data: List<ScienceItem>) = recyclerAdapter.replaceData(data)
}