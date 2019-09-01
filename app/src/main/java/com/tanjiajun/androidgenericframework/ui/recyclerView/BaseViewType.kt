package com.tanjiajun.androidgenericframework.ui.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil

/**
 * Created by TanJiaJun on 2019-08-31.
 */
abstract class BaseViewType<D> {

    @LayoutRes
    abstract fun getItemLayoutRes(): Int

    abstract fun isMatchViewType(any: Any): Boolean

    abstract fun bind(holder: BaseViewHolder,
                      data: D,
                      position: Int)

    abstract fun getSpanSize(): Int

    fun onCreateDataBindingViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
            BaseViewHolder(DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    viewType,
                    parent,
                    false
            ))

    open fun bind(holder: BaseViewHolder,
                  data: D,
                  position: Int,
                  payLoads: List<Any>) =
            holder.run {
                bind(this, data, position)
                binding.executePendingBindings()
            }

}