package ru.ikbfu.rectorapp.ui.adapters

import androidx.core.content.ContextCompat
import ru.ikbfu.rectorapp.R
import ru.ikbfu.rectorapp.model.data.server.model.FavoriteData
import ru.ikbfu.rectorapp.utils.delegate.DelegateAdapter
import kotlinx.android.synthetic.main.favorite_item.view.*

class FavoritesAdapter : DelegateAdapter<FavoriteData>() {


    override fun getLayoutId(): Int = R.layout.favorite_item

    override fun isForViewType(items: List<*>, position: Int): Boolean =
        items[position] is FavoriteData

    override fun onBind(item: FavoriteData, holder: DelegateViewHolder) = with(holder.itemView) {
        number_tv.text = item.currentValue.toString()
        main_text_tv.text = item.title
        path_tv.text = item.path
        seekBar_tv.text = resources.getString(R.string.stats_default, item.currentValue.toString(), item.maxValue.toString())
        seekBar.max = item.maxValue
        seekBar.progress = item.currentValue
        seekBar.setOnTouchListener { v, event -> true }
        when {
            item.currentValue >= (item.maxValue.toDouble() / 3) * 2 -> {
                grid_item.setBackgroundResource(R.drawable.favorite_item_background_green)
                seekBar.progressDrawable = ContextCompat.getDrawable(context, R.drawable.favorite_seekbar_green)
            }
            item.currentValue >= (item.maxValue.toDouble() / 3) -> {
                grid_item.setBackgroundResource(R.drawable.favorite_item_background_orange)
                seekBar.progressDrawable = ContextCompat.getDrawable(context, R.drawable.favorite_seekbar_orange)
            }
            item.currentValue < (item.maxValue.toDouble() / 3) -> {
                grid_item.setBackgroundResource(R.drawable.favorite_item_background_red)
                seekBar.progressDrawable = ContextCompat.getDrawable(context, R.drawable.favorite_seekbar_red)
            }
        }

    }
}