package com.tanjiajun.androidgenericframework.ui.order.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.tanjiajun.androidgenericframework.FRAGMENT_TAG_ORDER
import com.tanjiajun.androidgenericframework.R
import com.tanjiajun.androidgenericframework.databinding.FragmentOrderBinding
import com.tanjiajun.androidgenericframework.ui.BaseFragment
import com.tanjiajun.androidgenericframework.ui.order.adapter.OrderAdapter

/**
 * Created by TanJiaJun on 2019-09-01.
 */
class OrderFragment : BaseFragment() {

    private lateinit var binding: FragmentOrderBinding
    private var adapter = OrderAdapter()

    override fun getLayoutResource(): Int = R.layout.fragment_order

    override fun getTransactionTag(): String = FRAGMENT_TAG_ORDER

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            DataBindingUtil.inflate<FragmentOrderBinding>(inflater, getLayoutResource(), container, false)
                    .also { binding = it }
                    .root

    override fun onResume() {
        super.onResume()
        binding.rvOrder.run {
            layoutManager = LinearLayoutManager(context)
            adapter = this@OrderFragment.adapter
        }
    }

}