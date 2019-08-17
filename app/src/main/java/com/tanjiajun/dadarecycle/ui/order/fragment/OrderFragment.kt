package com.tanjiajun.dadarecycle.ui.order.fragment

import com.tanjiajun.dadarecycle.FRAGMENT_ORDER
import com.tanjiajun.dadarecycle.R
import com.tanjiajun.dadarecycle.ui.BaseFragment

/**
 * Created by TanJiaJun on 2019-08-18.
 */
class OrderFragment : BaseFragment() {

    override fun getLayoutResource(): Int = R.layout.fragment_order

    override fun getTransactionTag(): String = FRAGMENT_ORDER

}