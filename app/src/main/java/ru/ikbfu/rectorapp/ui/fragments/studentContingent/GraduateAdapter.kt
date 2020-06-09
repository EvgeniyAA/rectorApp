package ru.ikbfu.rectorapp.ui.fragments.studentContingent

import com.jakewharton.rxbinding2.widget.checkedChanges
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.graduet_item.view.*
import ru.ikbfu.rectorapp.R
import ru.ikbfu.rectorapp.extensions.visible
import ru.ikbfu.rectorapp.model.data.server.model.Graduate
import ru.ikbfu.rectorapp.utils.delegate.DelegateAdapter
import ru.ikbfu.rectorapp.utils.delegate.UserAction

class GraduateAdapter(private val editable: Boolean = false,
                      private val compositeDisposable: CompositeDisposable
) : DelegateAdapter<Graduate>() {

    override fun getLayoutId(): Int = R.layout.graduet_item

    override fun isForViewType(items: List<*>, position: Int): Boolean = items[position] is Graduate

    private val checkboxClick =
        PublishRelay.create<UserAction.ItemChecked<Graduate>>()

    override fun getAction(): Observable<UserAction<Graduate>> {
        val checkboxClick = checkboxClick.hide()

        return Observable.merge(listOf(checkboxClick))
    }

    override fun onBind(item: Graduate, holder: DelegateViewHolder) = with(holder.itemView) {
        checkBox.visible = editable
        if (checkBox.visible) {
            checkBox.isChecked = item.isFavorite
        }
        title.text = item.title
        first_value.text = item.firstValue.toString()
        second_value.text = item.secondValue.toString()
        total_value.text = (item.firstValue+item.secondValue).toString()

        checkBox.checkedChanges().skipInitialValue().subscribe {
            item.isFavorite = it
            checkboxClick.accept(UserAction.ItemChecked(item))
        }.bind()
    }

    private fun Disposable.bind() {
        compositeDisposable.add(this)
    }
}