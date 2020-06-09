package ru.ikbfu.rectorapp.ui.adapters

import kotlinx.android.synthetic.main.group_item.view.*
import ru.ikbfu.rectorapp.R
import ru.ikbfu.rectorapp.model.data.server.model.Category
import ru.ikbfu.rectorapp.model.data.server.model.DataType
import ru.ikbfu.rectorapp.model.data.server.model.ElementType
import ru.ikbfu.rectorapp.utils.delegate.DelegateAdapter

class GroupAdapter(
    private val onClick:(DataType)-> Unit
) : DelegateAdapter<Category>() {

    override fun getLayoutId(): Int = R.layout.group_item

    override fun isForViewType(items: List<*>, position: Int): Boolean =
        items[position] is Category

    override fun onBind(item: Category, holder: DelegateViewHolder) = with(holder.itemView) {
        title.text = item.title
        group_container.setOnClickListener { onClick(item.type) }
    }
}