package com.tanjiajun.androidgenericframework.ui.order.fragment

import com.tanjiajun.androidgenericframework.FRAGMENT_TAG_ORDER
import com.tanjiajun.androidgenericframework.R
import com.tanjiajun.androidgenericframework.ui.BaseFragment

/**
 * Created by TanJiaJun on 2019-09-01.
 */
class OrderFragment : BaseFragment() {

    override fun getLayoutResource(): Int = R.layout.fragment_order

    override fun getTransactionTag(): String = FRAGMENT_TAG_ORDER

}