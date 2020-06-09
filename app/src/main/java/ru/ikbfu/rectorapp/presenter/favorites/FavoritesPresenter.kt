package ru.ikbfu.rectorapp.presenter.favorites

import moxy.InjectViewState
import ru.ikbfu.rectorapp.Screens
import ru.ikbfu.rectorapp.global.BasePresenter
import ru.ikbfu.rectorapp.model.interactor.favorites.FavoritesInteractor
import ru.ikbfu.rectorapp.presenter.view.FavoritesView
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class FavoritesPresenter @Inject constructor(
    private val interactor: FavoritesInteractor,
    private val router: Router
) : BasePresenter<FavoritesView>() {

    fun getFavorites() =
        interactor.getFavorites()
            .doOnSubscribe { viewState.showDownLoadProgress(true) }
            .doAfterTerminate { viewState.showDownLoadProgress() }
            .subscribe({
                viewState.loadRecyclerData(it)
            },{
                Timber.e(it)
            }).bind()


    fun filterFavoritesByText(text:String) =
        interactor.filterFavorites(text)
            .subscribe({
                viewState.loadRecyclerData(it)
            },{
                Timber.e(it)
            }).bind()

    fun openMore() = router.navigateTo(Screens.MainCategoryScreen)
}