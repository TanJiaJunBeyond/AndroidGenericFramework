package com.tanjiajun.androidgenericframework.ui.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by TanJiaJun on 2019-08-29.
 */
abstract class BaseDataBindingAdapter
    : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
            BaseViewHolder(DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    viewType,
                    parent,
                    false
            ))

    override fun getItemViewType(position: Int): Int {
        return getLayoutResourceByPosition(position)
    }

    protected abstract fun getLayoutResourceByPosition(position: Int): Int

}