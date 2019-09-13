package com.tanjiajun.androidgenericframework.ui.order.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tanjiajun.androidgenericframework.EXTRA_POSITION
import com.tanjiajun.androidgenericframework.FRAGMENT_TAG_ORDER
import com.tanjiajun.androidgenericframework.R
import com.tanjiajun.androidgenericframework.databinding.FragmentOrderBinding
import com.tanjiajun.androidgenericframework.ui.BaseFragment
import com.tanjiajun.androidgenericframework.ui.order.adapter.OrderAdapter
import com.tanjiajun.androidgenericframework.ui.order.viewmodel.OrderViewModel
import com.tanjiajun.androidgenericframework.utils.getViewModelFactory

/**
 * Created by TanJiaJun on 2019-09-01.
 */
class OrderFragment : BaseFragment() {

    private var position = DEFAULT_POSITION
    private lateinit var binding: FragmentOrderBinding
    private val viewModel by viewModels<OrderViewModel> { getViewModelFactory() }
    private var adapter = OrderAdapter()

    override fun getLayoutResource(): Int = R.layout.fragment_order

    override fun getTransactionTag(): String = FRAGMENT_TAG_ORDER

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.run { position = getInt(EXTRA_POSITION, DEFAULT_POSITION) }
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
            adapter.setItems(viewModel.getOrderList(position))

    companion object {
        private const val DEFAULT_POSITION = 0

        fun newInstance(position: Int): OrderFragment =
                OrderFragment().apply {
                    arguments = Bundle().apply {
                        putInt(EXTRA_POSITION, position)
                    }
                }
    }

}