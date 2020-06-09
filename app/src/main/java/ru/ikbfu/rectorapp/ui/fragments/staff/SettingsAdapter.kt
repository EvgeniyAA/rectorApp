package ru.ikbfu.rectorapp.ui.fragments.staff

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import kotlinx.android.synthetic.main.settings_item.view.*
import ru.ikbfu.rectorapp.R
import ru.ikbfu.rectorapp.model.data.server.model.ShowParam
import ru.ikbfu.rectorapp.utils.delegate.DelegateAdapter
import ru.ikbfu.rectorapp.utils.delegate.UserAction

class SettingsAdapter : DelegateAdapter<ShowParam>() {
    override fun getLayoutId(): Int = R.layout.settings_item

    override fun isForViewType(items: List<*>, position: Int): Boolean =
        items[position] is ShowParam

    private val userClick = PublishRelay.create<UserAction<ShowParam>>()
    override fun getAction(): Observable<UserAction<ShowParam>> {
        return userClick.hide()
    }

    override fun onBind(item: ShowParam, holder: DelegateViewHolder) = with(holder.itemView) {
        title.text = item.title
        switchBtn.isChecked = item.needToShow
        settings_container.setOnClickListener {
            item.needToShow = !item.needToShow
            switchBtn.isChecked = item.needToShow
            userClick.accept(UserAction.ItemPressed(item))
        }
        switchBtn.setOnClickListener {
            item.needToShow = !item.needToShow
            switchBtn.isChecked = item.needToShow
            userClick.accept(UserAction.ItemPressed(item))
        }
    }
}