package com.tanjiajun.androidgenericframework.ui.repository.adapter

import com.tanjiajun.androidgenericframework.BR
import com.tanjiajun.androidgenericframework.R
import com.tanjiajun.androidgenericframework.data.model.repository.RepositoryData
import com.tanjiajun.androidgenericframework.ui.recyclerView.BaseViewHolder
import com.tanjiajun.androidgenericframework.ui.recyclerView.BaseViewType
import com.tanjiajun.androidgenericframework.ui.recyclerView.MultiViewTypeDataBindingAdapter

/**
 * Created by TanJiaJun on 2020-02-07.
 */
class RepositoryAdapter : MultiViewTypeDataBindingAdapter<RepositoryData>() {

    init {
        addViewType(RepositoryViewType())
    }

}

class RepositoryViewType : BaseViewType<RepositoryData>() {

    override fun getItemLayoutRes(): Int = R.layout.item_repository

    override fun isMatchViewType(any: Any): Boolean =
            any is RepositoryData

    override fun bind(holder: BaseViewHolder, data: RepositoryData, position: Int) {
        holder.binding.setVariable(BR.data, data)
    }

    override fun getSpanSize(): Int = 0

}