package ru.ikbfu.rectorapp.ui.fragments.staff

import kotlinx.android.synthetic.main.staff_fragment.*
import ru.ikbfu.rectorapp.R
import com.jakewharton.rxbinding3.material.selections
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.ikbfu.rectorapp.model.interactor.staff.StaffInteractor
import ru.ikbfu.rectorapp.model.repository.staff.StaffRepository
import ru.ikbfu.rectorapp.presenter.staff.StaffPresenter
import ru.ikbfu.rectorapp.presenter.view.StaffView
import ru.ikbfu.rectorapp.ui.fragments.base.MvpBaseFlowFragment
import toothpick.Scope
import toothpick.config.Module

class StaffFragment : MvpBaseFlowFragment(), StaffView {
    override val containerId: Int = R.id.staff_container
    override val layout: Int = R.layout.staff_fragment

    companion object {
        fun newInstance() = StaffFragment()
    }

    @InjectPresenter
    lateinit var presenter: StaffPresenter

    @ProvidePresenter
    fun providePresenter(): StaffPresenter = scope
        .getInstance(StaffPresenter::class.java)

    override fun installModules(scope: Scope) {
        scope.installModules(object: Module(){
            init {
                bind(StaffRepository::class.java).singleton()
                bind(StaffInteractor::class.java).singleton()
            }
        })
        super.installModules(scope)
    }


    override fun tabSwitch() =
        tabs.selections().map {
            if (it.position == 0) StaffTab.HierarchyTab
            else StaffTab.ParametersTab
        }.subscribe {
            when (it) {
                is StaffTab.ParametersTab -> presenter.showParameters()
                is StaffTab.HierarchyTab -> presenter.showHierarchy()
            }
        }.bind()

    override fun showDownLoadProgress(isVisible: Boolean) {
        showProgressDialog(isVisible)
    }

    override fun onBackPressed() = presenter.onBackPressed()

}