package com.tanjiajun.androidgenericframework.ui.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by TanJiaJun on 2019-08-29.
 */
abstract class BaseDataBindingAdapter<D : Any>
    : RecyclerView.Adapter<BaseViewHolder>() {

    protected abstract fun getLayoutResByPosition(position: Int): Int

    protected abstract fun getItemByPosition(position: Int): D?

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
            BaseViewHolder(DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    viewType,
                    parent,
                    false
            ))

    override fun getItemViewType(position: Int): Int =
            getLayoutResByPosition(position)

}