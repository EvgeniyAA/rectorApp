package ru.ikbfu.rectorapp.ui.fragments.favorites

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_favorites.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.ikbfu.rectorapp.R
import ru.ikbfu.rectorapp.extensions.rxTextChanges
import ru.ikbfu.rectorapp.extensions.subscribeBy
import ru.ikbfu.rectorapp.model.data.server.model.FavoriteData
import ru.ikbfu.rectorapp.presenter.favorites.FavoritesPresenter
import ru.ikbfu.rectorapp.presenter.view.FavoritesView
import ru.ikbfu.rectorapp.toothpick.DI
import ru.ikbfu.rectorapp.toothpick.Injectable
import ru.ikbfu.rectorapp.ui.adapters.FavoritesAdapter
import ru.ikbfu.rectorapp.ui.fragments.base.MvpBaseFragment
import ru.ikbfu.rectorapp.utils.delegate.CompositeDelegateAdapter
import toothpick.Toothpick
import java.util.concurrent.TimeUnit

class FavoritesFragment : MvpBaseFragment(), FavoritesView {
    override val layout: Int
        get() = R.layout.fragment_favorites

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    @InjectPresenter
    lateinit var presenter: FavoritesPresenter

    @ProvidePresenter
    fun providePresenter(): FavoritesPresenter = scope
        .getInstance(FavoritesPresenter::class.java)


    private lateinit var favoritesAdapter: CompositeDelegateAdapter<FavoriteData>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        RxTextView.textChanges(search_favorites_et)
            .skipInitialValue()
            .debounce(300L, TimeUnit.MILLISECONDS)
            .map { it.toString() }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy({ text ->
                presenter.filterFavoritesByText(text)
            }).bind()
        add_more_btn.setOnClickListener {
            presenter.openMore()
        }
        presenter.getFavorites()

    }

    override fun showDownLoadProgress(isVisible: Boolean) {
        showProgressDialog(isVisible)
    }

    private fun initRecycler() {
        favorites_recycler.layoutManager = setLayoutManager()
        favoritesAdapter = CompositeDelegateAdapter.Companion.Builder<FavoriteData>()
            .add(FavoritesAdapter())
            .build()
        favorites_recycler.adapter = favoritesAdapter
    }

    override fun loadRecyclerData(data: List<FavoriteData>) {
        favoritesAdapter.replaceData(data)
    }

    private fun setLayoutManager() = GridLayoutManager(context!!, 3, RecyclerView.VERTICAL, false)
}