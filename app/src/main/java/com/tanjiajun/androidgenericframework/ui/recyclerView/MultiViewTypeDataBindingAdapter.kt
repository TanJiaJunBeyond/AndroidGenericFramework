package com.tanjiajun.androidgenericframework.ui.recyclerView

import android.util.SparseArray
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by TanJiaJun on 2019-08-31.
 */
abstract class MultiViewTypeDataBindingAdapter<D : Any>
    : BaseDataBindingAdapter<D>() {

    private val items = mutableListOf<D>()
    val viewTypes = SparseArray<BaseViewType<D>>()
    val noDataViewTypes = SparseArray<NoDataViewType<D>>()
    var headerCount = 0
    var footerCount = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
            viewTypes[viewType, noDataViewTypes[viewType]]
                    .onCreateDataBindingViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        bindViewType(holder, position)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int, payloads: MutableList<Any>) {
        payloads
                .takeIf { it.isNotEmpty() }
                ?.let {
                    bindViewType(holder, position, payloads)
                }
    }

    override fun getItemCount(): Int =
            headerCount + items.size + footerCount

    override fun getItemByPosition(position: Int): D? =
            items[position]

    override fun getLayoutResByPosition(position: Int): Int =
            getViewTypeByPosition(position).getItemLayoutRes()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager
                ?.let {
                    if (it is GridLayoutManager) {
                        it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                            override fun getSpanSize(position: Int): Int =
                                    getViewTypeByPosition(position).getSpanSize()
                        }
                    }
                }
    }

    private fun bindViewType(holder: BaseViewHolder,
                             position: Int) =
            viewTypes[holder.itemViewType]
                    ?.run {
                        bind(holder, items[getItemPositionByPosition(position)], position)
                    }
                    ?: noDataViewTypes[holder.itemViewType].bind(holder, position)

    private fun bindViewType(holder: BaseViewHolder,
                             position: Int,
                             payloads: MutableList<Any>) =
            viewTypes[holder.itemViewType]
                    ?.run {
                        bind(holder, items[getItemPositionByPosition(position)], position, payloads)
                    }
                    ?: noDataViewTypes[holder.itemViewType].bind(holder, position, payloads)

    private fun getItemPositionByPosition(position: Int): Int =
            position - headerCount

    fun getViewTypeByPosition(position: Int): BaseViewType<D> {
        val noDataViewTypesSize = noDataViewTypes.size()

        for (i in 0 until noDataViewTypesSize) {
            val noDataViewType = noDataViewTypes[i]

            if (noDataViewType.isMatchViewType(position)) {
                return noDataViewType
            }
        }

        val viewTypesSize = viewTypes.size()

        for (i in 0 until viewTypesSize) {
            val viewType = viewTypes[i]

            if (viewType.isMatchViewType(position)) {
                return viewType
            }
        }

        throw IllegalStateException("View type not find.")
    }

    fun addHeaderViewType(viewType: NoDataViewType<D>) {
        noDataViewTypes.put(viewType.getItemLayoutRes(), viewType)
        headerCount++
    }

    fun addViewType(viewType: BaseViewType<D>) =
            viewTypes.put(viewType.getItemLayoutRes(), viewType)

    fun addFooterViewType(viewType: NoDataViewType<D>) {
        noDataViewTypes.put(viewType.getItemLayoutRes(), viewType)
        footerCount++
    }

    fun removeHeaderViewType(key: Int): Boolean =
            noDataViewTypes
                    .takeIf { it[key] != null }
                    ?.let {
                        it.remove(key)
                        headerCount--
                        true
                    }
                    ?: false

    fun findItemsHasMatchViewType(items: List<D>): List<D> =
            mutableListOf<D>()
                    .apply {
                        val viewTypesSize = viewTypes.size()

                        items.forEach {
                            for (i in 0 until viewTypesSize) {
                                if (viewTypes.valueAt(i).isMatchViewType(it)) {
                                    add(it)
                                }
                            }
                        }
                    }

    fun setItems(items: List<D>) =
            this.items.run {
                clear()
                addAll(findItemsHasMatchViewType(items))
            }

    fun addItems(items: List<D>) =
            this.items.run {
                addAll(findItemsHasMatchViewType(items))
                notifyItemRangeInserted(headerCount + size, items.size)
            }

    fun updateAllItems(items: List<D>) {
        setItems(items)
        notifyDataSetChanged()
    }

}