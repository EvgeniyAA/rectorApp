package ru.ikbfu.rectorapp.presenter.science

import moxy.InjectViewState
import ru.ikbfu.rectorapp.global.BasePresenter
import ru.ikbfu.rectorapp.model.interactor.science.ScienceInteractor
import ru.ikbfu.rectorapp.presenter.view.ScienceView
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class SciencePresenter @Inject constructor(
    private val interactor: ScienceInteractor,
    private val router: Router
) : BasePresenter<ScienceView>() {

    fun getScienceData() = interactor
        .getScienceData()
        .doOnSubscribe { viewState.showDownLoadProgress(true) }
        .doAfterTerminate { viewState.showDownLoadProgress() }
        .subscribe({
            viewState.setupData(it)
        }, {
            Timber.e(it)
        }).bind()

    fun filterScienceData(text: String) = interactor
        .filterScienceData(text)
        .subscribe({
            viewState.setupData(it)
        },{
            Timber.e(it)
        }).bind()

    fun onBackPressed() = router.exit()
}