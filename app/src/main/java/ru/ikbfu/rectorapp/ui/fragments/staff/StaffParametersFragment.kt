package ru.ikbfu.rectorapp.ui.fragments.staff

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.staff_parameters_fragment.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.ikbfu.rectorapp.R
import ru.ikbfu.rectorapp.model.data.server.model.ShowParam
import ru.ikbfu.rectorapp.presenter.staff.StaffParametersPresenter
import ru.ikbfu.rectorapp.presenter.view.StaffParametersView
import ru.ikbfu.rectorapp.ui.fragments.base.MvpBaseFragment
import ru.ikbfu.rectorapp.utils.delegate.CompositeDelegateAdapter
import ru.ikbfu.rectorapp.utils.delegate.pressedItems

class StaffParametersFragment : MvpBaseFragment(), StaffParametersView {
    override val layout: Int = R.layout.staff_parameters_fragment
    companion object{
        fun newInstance() = StaffParametersFragment()
    }

    @InjectPresenter
    lateinit var presenter: StaffParametersPresenter

    @ProvidePresenter
    fun providePresenter(): StaffParametersPresenter = scope
        .getInstance(StaffParametersPresenter::class.java)

    private lateinit var settingsAdapter: CompositeDelegateAdapter<ShowParam>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsAdapter = CompositeDelegateAdapter.Companion.Builder<ShowParam>()
            .add(SettingsAdapter())
            .build()
        staff_settings.adapter = settingsAdapter
        settingsAdapter.actions.pressedItems().subscribe {
            presenter.itemPressed(it)
        }.bind()
    }

    override fun setupParams(items: List<ShowParam>) {
        settingsAdapter.replaceData(items)
    }
}