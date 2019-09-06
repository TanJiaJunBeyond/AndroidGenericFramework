package com.tanjiajun.androidgenericframework.ui.order.adapter

import com.tanjiajun.androidgenericframework.BR
import com.tanjiajun.androidgenericframework.R
import com.tanjiajun.androidgenericframework.data.model.order.response.OrderData
import com.tanjiajun.androidgenericframework.ui.recyclerView.BaseViewHolder
import com.tanjiajun.androidgenericframework.ui.recyclerView.BaseViewType
import com.tanjiajun.androidgenericframework.ui.recyclerView.MultiViewTypeDataBindingAdapter

/**
 * Created by TanJiaJun on 2019-09-01.
 */
class OrderAdapter : MultiViewTypeDataBindingAdapter<OrderData>() {

    init {
        addViewType(OrderViewType())
    }

}

class OrderViewType : BaseViewType<OrderData>() {

    override fun getItemLayoutRes(): Int = R.layout.item_order

    override fun isMatchViewType(any: Any): Boolean =
            any is OrderData

    override fun bind(holder: BaseViewHolder, data: OrderData, position: Int) {
        holder.binding.setVariable(BR.data, data)
    }

    override fun getSpanSize(): Int = 0

}