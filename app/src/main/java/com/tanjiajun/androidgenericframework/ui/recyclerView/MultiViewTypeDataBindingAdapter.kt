package com.tanjiajun.androidgenericframework.ui.recyclerView

import android.util.SparseArray
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by TanJiaJun on 2019-08-31.
 */
abstract class MultiViewTypeDataBindingAdapter<T : Any>
    : BaseDataBindingAdapter<T>() {

    private val items = mutableListOf<T>()
    private val viewTypes = SparseArray<BaseViewType<T>>()
    private val noDataViewTypes = SparseArray<NoDataViewType<T>>()
    private var headerCount = 0
    private var footerCount = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
            viewTypes[viewType, noDataViewTypes[viewType]]
                    .onCreateDataBindingViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        bindViewType(holder, position)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        payloads
                .takeIf { it.isNotEmpty() }
                ?.let {
                    bindViewType(holder, position, payloads)
                }
    }

    override fun getItemCount(): Int =
            headerCount + items.size + footerCount

    override fun getItemByPosition(position: Int): T? =
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

    fun getViewTypeByPosition(position: Int): BaseViewType<T> {
        val noDataViewTypesSize = noDataViewTypes.size()

        for (i in 0 until noDataViewTypesSize) {
            val noDataViewType = noDataViewTypes.valueAt(i)

            if (noDataViewType.isMatchViewType(position, headerCount, items.size, footerCount)) {
                return noDataViewType
            }
        }

        val viewTypesSize = viewTypes.size()

        for (i in 0 until viewTypesSize) {
            val viewType = viewTypes.valueAt(i)

            if (viewType.isMatchViewType(items[getItemPositionByPosition(position)])) {
                return viewType
            }
        }

        throw IllegalStateException("View type not find.")
    }

    fun addHeaderViewType(viewType: NoDataViewType<T>) {
        noDataViewTypes.put(viewType.getItemLayoutRes(), viewType)
        headerCount++
    }

    fun addViewType(viewType: BaseViewType<T>) =
            viewTypes.put(viewType.getItemLayoutRes(), viewType)

    fun addFooterViewType(viewType: NoDataViewType<T>) {
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

    private fun findItemsHasMatchViewType(items: List<T>): List<T> =
            mutableListOf<T>()
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

    fun setItems(items: List<T>) =
            with(this.items) {
                if (isNotEmpty()) clear()
                addAll(findItemsHasMatchViewType(items))
                notifyDataSetChanged()
            }

    fun addItems(items: List<T>) =
            with(this.items) {
                addAll(findItemsHasMatchViewType(items))
                notifyItemRangeInserted(headerCount + size, items.size)
            }

}