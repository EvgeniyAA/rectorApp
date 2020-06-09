package ru.ikbfu.rectorapp.ui.widgets

import android.content.Context
import android.graphics.Typeface
import android.view.View
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.tree_item.view.*
import kotlinx.android.synthetic.main.tree_item_container.view.*
import ru.ikbfu.rectorapp.R
import ru.ikbfu.rectorapp.model.data.server.model.SelectionCommitteeElement
import ru.ikbfu.rectorapp.model.data.server.model.ElementType

//class FormBuilder(private val context: Context) {
//    private var editMode = false
//    private var data: List<SelectionCommitteeElement> = listOf()
//    private var idsWhoNeedChildSeparator: MutableList<String> = mutableListOf()
//
//    fun editMode(value: Boolean) = apply { this.editMode = value }
//
//    fun getData(value: List<SelectionCommitteeElement>) = apply { this.data = value }
//
//    fun build(): View {
//        val rootViews = data.filter { x -> x.elementType == ElementType.ROOT_DATA }
//        val view = View.inflate(context, R.layout.tree_item_container, null)
//        val withoutChildsData = data.filter { x -> x.childIds.isNullOrEmpty() }.map { it.id }
//        data.forEach { x ->
//            if (!x.childIds.isNullOrEmpty() && x.childIds.any { y -> withoutChildsData.contains(y) })
//                idsWhoNeedChildSeparator.add(x.childIds.last())
//        }
//        rootViews.forEach { x ->
//            createViews(view, x)
//        }
//        return view
//    }
//
//    private fun createViews(view: View, item: SelectionCommitteeElement) {
//        val itemView = when (item.elementType) {
//            ElementType.ROOT_DATA -> createRootView(item)
//            ElementType.CHILD_DATA -> createChildView(item)
//        }
//
//        view.tree_item_container.addView(itemView)
//
//        val viewContainerForChilds = View.inflate(context, R.layout.tree_item_container, null)
//
//        itemView.item_container.addView(viewContainerForChilds)
//
//        setupClickListener(itemView, viewContainerForChilds, item)
//        if (!item.childIds.isNullOrEmpty())
//            data.filter { x -> item.childIds.contains(x.id) }.forEach {
//                createViews(viewContainerForChilds, it)
//            }
//    }
//
//    private fun createRootView(item: SelectionCommitteeElement): View {
//        val elementView: View = View.inflate(context, R.layout.tree_item, null)
//        elementView.title.text = item.name
//        elementView.value.text = item.value
//        elementView.value.setBackgroundResource(R.drawable.blue_background)
//        elementView.value.setTextColor(ContextCompat.getColor(context, R.color.white))
//        elementView.value.setTypeface(elementView.value.typeface, Typeface.BOLD)
//        elementView.main_underline.visibility =
//            if (item.childIds.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE
//        if (item.childIds.isNullOrEmpty())
//        elementView.imageView.setBackgroundResource(R.color.transparent)
//            //if (item.childIds.isNullOrEmpty()) R.color.transparent else View.VISIBLE
//        return elementView
//    }
//
//    private fun createChildView(item: SelectionCommitteeElement): View {
//        val elementView: View = View.inflate(context, R.layout.tree_item, null)
//        elementView.title.text = item.name
//        elementView.value.text = item.value
//
//        when {
//            !item.childIds.isNullOrEmpty() ->{
//                elementView.imageView.setImageResource(R.drawable.ic_minus_black)
//                elementView.imageView.setBackgroundResource(R.color.transparent)//expand_background.visibility = View.INVISIBLE
//                elementView.value.setBackgroundResource(R.color.transparent)
//                if (data.first { x -> item.childIds.contains(x.id) }.childIds.isNullOrEmpty()) {
//                    elementView.child_underline.visibility = View.VISIBLE
//                    elementView.value.setTypeface(elementView.value.typeface, Typeface.BOLD)
//                }
//            }
//            else ->{
//                elementView.imageView.visibility = View.INVISIBLE
//                elementView.imageView.setBackgroundResource(R.color.transparent)//expand_background.visibility = View.INVISIBLE
//                elementView.value.setTextColor(ContextCompat.getColor(context, R.color.white))
//
//                if (idsWhoNeedChildSeparator.contains(item.id))
//                    elementView.child_underline.visibility = View.VISIBLE
//
//                calculateValueBackground(
//                    item.value.toDouble(),
//                    item.maxValue!!.toDouble(),
//                    elementView.value
//                )
//            }
//        }
//        return elementView
//    }
//
//    private fun setupClickListener(
//        elementView: View,
//        viewContainerForChilds: View,
//        item: SelectionCommitteeElement
//    ) {
//        if (!item.childIds.isNullOrEmpty())
//            elementView.clickable_container.setOnClickListener {
//                when (viewContainerForChilds.visibility) {
//                    View.GONE -> {
//                        when {
//                            item.elementType == ElementType.ROOT_DATA -> {
//                                elementView.imageView.setImageResource(R.drawable.ic_minus_white)
//                                elementView.main_underline.visibility = View.VISIBLE
//                            }
//                            else -> {
//                                elementView.imageView.setImageResource(R.drawable.ic_minus_black)
//                                if (data.first { x ->
//                                        !item.childIds.isNullOrEmpty() && item.childIds.contains(
//                                            x.id
//                                        )
//                                    }.childIds.isNullOrEmpty()) {
//                                    elementView.child_underline.visibility = View.VISIBLE
//                                }
//                            }
//                        }
//                        viewContainerForChilds.visibility = View.VISIBLE
//                    }
//                    View.VISIBLE -> {
//                        when {
//                            item.elementType == ElementType.ROOT_DATA -> {
//                                elementView.imageView.setImageResource(R.drawable.ic_plus_white)
//                                elementView.main_underline.visibility = View.INVISIBLE
//                            }
//                            else -> {
//                                elementView.imageView.setImageResource(R.drawable.ic_plus_black)
//                                if (data.first { x ->
//                                        !item.childIds.isNullOrEmpty() && item.childIds.contains(
//                                            x.id
//                                        )
//                                    }.childIds.isNullOrEmpty()) {
//                                    elementView.child_underline.visibility = View.INVISIBLE
//                                }
//                            }
//                        }
//                        viewContainerForChilds.visibility = View.GONE
//                    }
//                }
//
//            }
//    }
//
//
//    private fun calculateValueBackground(currentValue: Double, maxValue: Double, view: View) {
//        when {
//            currentValue >= (maxValue / 3) * 2 -> {
//                view.setBackgroundResource(R.drawable.item_background_green)
//            }
//            currentValue >= (maxValue / 3) -> {
//                view.setBackgroundResource(R.drawable.item_background_orange)
//            }
//            currentValue < (maxValue / 3) -> {
//                view.setBackgroundResource(R.drawable.item_background_red)
//
//            }
//        }
//    }
//}
