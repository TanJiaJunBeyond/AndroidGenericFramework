package com.tanjiajun.androidgenericframework.ui.recyclerView

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.tanjiajun.androidgenericframework.BR

/**
 * Created by TanJiaJun on 2019-08-25.
 */
class BaseViewHolder(val binding: ViewDataBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: Any) =
            binding.run {
                setVariable(BR.data, data)
                executePendingBindings()
            }

}