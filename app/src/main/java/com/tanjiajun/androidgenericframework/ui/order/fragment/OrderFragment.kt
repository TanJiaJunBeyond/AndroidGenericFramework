package com.tanjiajun.androidgenericframework.ui.order.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.tanjiajun.androidgenericframework.EXTRA_POSITION
import com.tanjiajun.androidgenericframework.FRAGMENT_TAG_ORDER
import com.tanjiajun.androidgenericframework.R
import com.tanjiajun.androidgenericframework.data.model.order.response.OrderData
import com.tanjiajun.androidgenericframework.databinding.FragmentOrderBinding
import com.tanjiajun.androidgenericframework.ui.BaseFragment
import com.tanjiajun.androidgenericframework.ui.order.adapter.OrderAdapter

/**
 * Created by TanJiaJun on 2019-09-01.
 */
class OrderFragment : BaseFragment() {

    private var position = 0
    private lateinit var binding: FragmentOrderBinding
    private var adapter = OrderAdapter()

    override fun getLayoutResource(): Int = R.layout.fragment_order

    override fun getTransactionTag(): String = FRAGMENT_TAG_ORDER

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.run { position = getInt(EXTRA_POSITION, 0) }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            DataBindingUtil.inflate<FragmentOrderBinding>(inflater, getLayoutResource(), container, false)
                    .also { binding = it }
                    .root

    override fun onResume() {
        super.onResume()
        initUI()
        initData()
    }

    private fun initUI() =
            binding.rvOrder.run {
                layoutManager = LinearLayoutManager(context)
                adapter = this@OrderFragment.adapter
            }

    private fun initData() =
            adapter.setItems(mutableListOf<OrderData>()
                    .apply {
                        for (i in 0 until 50) {
                            add(OrderData(
                                    id = i.toLong(),
                                    title = "第${position}个订单",
                                    orderNumber = i.toString(),
                                    address = "地址$i",
                                    date = "2019年9月7日",
                                    weight = 100,
                                    noteInformation = "谭嘉俊",
                                    firstImageUrl = COMMON_IMAGE_URL,
                                    secondImageUrl = COMMON_IMAGE_URL,
                                    thirdImageUrl = COMMON_IMAGE_URL
                            ))
                        }
                    })

    companion object {
        private const val COMMON_IMAGE_URL = "https://qa-media-api.xogrp.com/images/3932fa03-2437-4ca2-b413-b88ac261bc0f"

        fun newInstance(position: Int): OrderFragment =
                OrderFragment().apply {
                    arguments = Bundle().apply {
                        putInt(EXTRA_POSITION, position)
                    }
                }
    }

}