package ru.ikbfu.rectorapp.ui.fragments.favorites

import android.os.Bundle
import moxy.InjectViewState
import moxy.MvpView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.ikbfu.rectorapp.R
import ru.ikbfu.rectorapp.Screens
import ru.ikbfu.rectorapp.extensions.setLaunchScreen
import ru.ikbfu.rectorapp.global.BasePresenter
import ru.ikbfu.rectorapp.ui.fragments.base.MvpBaseFlowFragment
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class FavoritesFlowFragment : MvpBaseFlowFragment(){//, FavoritesFlowView {
    override val containerId: Int
        get() = R.id.favorites_flow_container
    override val layout: Int
        get() = R.layout.favorites_flow_fragment

    //@InjectPresenter
    //lateinit var presenter: FavoritesFlowPresenter

    //@ProvidePresenter
    //fun providePresenter() = scope
    //    .getInstance(FavoritesFlowPresenter::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(childFragmentManager.fragments.isEmpty())
            navigator.setLaunchScreen(Screens.FavoritesScreen)
    }
}

//@InjectViewState
//class FavoritesFlowPresenter @Inject constructor(private val router: Router) :
//    BasePresenter<FavoritesFlowView>() {
//    override fun onFirstViewAttach() {
//        super.onFirstViewAttach()
//            router.navigateTo(Screens.FavoritesScreen)
//
//    }
//}
//
//@StateStrategyType(AddToEndSingleStrategy::class)
//interface FavoritesFlowView : MvpView