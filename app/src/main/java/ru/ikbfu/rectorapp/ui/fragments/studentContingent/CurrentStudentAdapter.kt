package ru.ikbfu.rectorapp.ui.fragments.studentContingent

import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.widget.checkedChanges
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.tree_item.view.*
import ru.ikbfu.rectorapp.R
import ru.ikbfu.rectorapp.extensions.inflate
import ru.ikbfu.rectorapp.extensions.visible
import ru.ikbfu.rectorapp.model.data.server.model.ElementType
import ru.ikbfu.rectorapp.model.data.server.model.Student
import ru.ikbfu.rectorapp.model.data.server.model.UnderlineType
import ru.ikbfu.rectorapp.utils.delegate.DelegateAdapter
import ru.ikbfu.rectorapp.utils.delegate.UserAction
import timber.log.Timber

class CurrentStudentAdapter(
    private val editable: Boolean = false,
    private val data: MutableList<Student>,
    private val onItemSelected: (item: Student) -> Unit,
    private val onItemChecked: (item: Student) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder =
        ViewHolder(parent.inflate(R.layout.tree_item))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as ViewHolder).bind(data[position])

    fun replaceData(newData: List<Student>) {
        val result = DiffUtil.calculateDiff(TreeDiffUtills(newData, data))
        result.dispatchUpdatesTo(this)
        data.clear()
        data.addAll(newData)
        //this.notifyDataSetChanged()
    }

    class TreeDiffUtills(val newData: List<Student>, val oldData: List<Student>) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldData[oldItemPosition].id == newData[newItemPosition].id

        override fun getOldListSize(): Int = oldData.size
        override fun getNewListSize(): Int = newData.size
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldData[oldItemPosition].isCollapsed == newData[newItemPosition].isCollapsed && oldData[oldItemPosition].isFavorite == newData[newItemPosition].isFavorite && oldData[oldItemPosition].id == newData[newItemPosition].id
    }

    private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Student) {

            with(itemView) {

                item_container.title.text = item.name
                item_container.value.text = item.value.toString()
                checkBox.visibility = if (editable) View.VISIBLE else View.INVISIBLE
                if (checkBox.visible) {
                    checkBox.isChecked = item.isFavorite
                }
                if (!item.color.isNullOrEmpty())
                    item_container.value.setBackgroundColor(Color.parseColor(item.color))
                else
                    item_container.value.setBackgroundResource(R.color.transparent)

                when (item.elementType) {
                    ElementType.CHILD_DATA -> {


                        if (item.isCollapsed) item_container.imageView.setImageResource(R.drawable.ic_plus_black)
                        else item_container.imageView.setImageResource(R.drawable.ic_minus_black)

                        item_container.imageView.setBackgroundResource(R.color.transparent)
                        item_container.value.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.text_color_black
                            )
                        )
                        item_container.imageView.visibility = View.VISIBLE

                        item_container.value.typeface =
                            Typeface.create(item_container.value.typeface, Typeface.NORMAL)
                    }
                    ElementType.ROOT_DATA -> {
                        item_container.value.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.white
                            )
                        )
                        item_container.imageView.visibility = View.INVISIBLE
                        item_container.value.typeface =
                            Typeface.create(item_container.value.typeface, Typeface.NORMAL)
                    }
                    ElementType.PARENT_DATA -> {


                        item_container.value.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.white
                            )
                        )
                        item_container.imageView.visibility = View.VISIBLE
                        item_container.value.setTypeface(
                            item_container.value.typeface,
                            Typeface.BOLD
                        )

                        if (item.isCollapsed)
                            item_container.imageView.setImageResource(R.drawable.ic_plus_white)
                        else item_container.imageView.setImageResource(R.drawable.ic_minus_white)

                        item_container.imageView.setBackgroundResource(R.drawable.rounded_blue_background)
                        item_container.main_underline.visibility =
                            if (item.childIds.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE
                    }
                }

                when (item.underlined) {
                    UnderlineType.FULL -> {
                        item_container.main_underline.visibility =
                            when {
                                item.childIds.isNullOrEmpty() -> View.GONE
                                item.isCollapsed -> View.GONE
                                else -> View.VISIBLE
                            }
                        item_container.child_underline.visible = false
                    }
                    UnderlineType.PART -> {
                        item_container.main_underline.visible = false
                        item_container.child_underline.visibility =
                            if (item.isCollapsed) View.GONE else View.VISIBLE
                    }
                    UnderlineType.NONE -> {
                        item_container.child_underline.visible = false
                        item_container.main_underline.visible = false
                    }
                }
                if (item.childIds != null || item.elementType == ElementType.PARENT_DATA)
                    item_container.setOnClickListener {
                        Timber.e(item.name)
                        onItemSelected(item)
                    }

                checkBox.setOnClickListener {
                    Timber.e("$item")
                    item.isFavorite = !item.isFavorite
                    onItemChecked(item)
                }
            }
        }
    }
}