package com.tanjiajun.androidgenericframework.ui.order.viewmodel

import com.tanjiajun.androidgenericframework.data.model.order.response.OrderData
import com.tanjiajun.androidgenericframework.data.repository.UserRepository
import com.tanjiajun.androidgenericframework.ui.BaseViewModel

/**
 * Created by TanJiaJun on 2019-09-13.
 */
class OrderViewModel(
        private val repository: UserRepository
) : BaseViewModel() {

    fun getOrderList(position: Int): List<OrderData> =
            mutableListOf<OrderData>()
                    .apply {
                        for (i in 0 until 100) {
                            add(OrderData(
                                    id = i.toLong(),
                                    title = repository.getUserInfo()?.run { "${userName}的第${position}个订单" }
                                            ?: "",
                                    orderNumber = "订单号：$i",
                                    address = repository.getUserInfo()?.run { "地址：${userName}的地址" }
                                            ?: "",
                                    date = "日期：2019年9月7日",
                                    weight = 100,
                                    noteInformation = repository.getUserInfo()?.run { "备注信息：${userName}的备注信息" }
                                            ?: "",
                                    firstImageUrl = "https://qa-media-api.xogrp.com/images/3932fa03-2437-4ca2-b413-b88ac261bc0f",
                                    secondImageUrl = "https://qa-media-api.xogrp.com/images/error",
                                    thirdImageUrl = null
                            ))
                        }
                    }

}