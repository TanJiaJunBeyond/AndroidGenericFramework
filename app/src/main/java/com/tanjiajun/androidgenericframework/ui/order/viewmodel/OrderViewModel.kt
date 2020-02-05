package com.tanjiajun.androidgenericframework.ui.order.viewmodel

import com.tanjiajun.androidgenericframework.data.model.order.response.OrderData
import com.tanjiajun.androidgenericframework.data.repository.UserInfoRepository
import com.tanjiajun.androidgenericframework.ui.BaseViewModel

/**
 * Created by TanJiaJun on 2019-09-13.
 */
class OrderViewModel(
        private val repository: UserInfoRepository
) : BaseViewModel() {

    fun getOrderList(position: Int): List<OrderData> =
            mutableListOf<OrderData>()
                    .apply {
                        for (i in 0 until 100) {
                            add(OrderData(
                                    id = i.toLong(),
                                    title = "",
                                    orderNumber = "订单号：$i",
                                    address = "",
                                    date = "日期：2019年9月7日",
                                    weight = 100,
                                    noteInformation = "",
                                    firstImageUrl = "https://qa-media-api.xogrp.com/images/3932fa03-2437-4ca2-b413-b88ac261bc0f",
                                    secondImageUrl = "https://qa-media-api.xogrp.com/images/error",
                                    thirdImageUrl = null
                            ))
                        }
                    }

}