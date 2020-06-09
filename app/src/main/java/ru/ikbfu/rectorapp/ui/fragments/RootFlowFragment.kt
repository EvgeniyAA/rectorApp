package ru.ikbfu.rectorapp.ui.fragments

import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_flow_root.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.ikbfu.rectorapp.R
import ru.ikbfu.rectorapp.presenter.RootFlowPresenter
import ru.ikbfu.rectorapp.presenter.view.RootFlowView
import ru.ikbfu.rectorapp.ui.fragments.base.MvpBaseFlowFragment

class RootFlowFragment : MvpBaseFlowFragment(), RootFlowView {
    override val containerId: Int
        get() = R.id.root_flow_container

    override val layout: Int = R.layout.fragment_flow_root

    @InjectPresenter
    lateinit var presenter: RootFlowPresenter

    @ProvidePresenter
    fun providePresenter() = scope
        .getInstance(RootFlowPresenter::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null)
            presenter.goToHome()

    }

    override fun onResume() {
        super.onResume()

        bottom_bar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_start -> presenter.goToHome()
                R.id.menu_group -> presenter.goToGroup()
                R.id.menu_favorites -> presenter.goToFavorites()
            }
            true
        }
        bottom_bar.setOnNavigationItemReselectedListener {  }
    }
}