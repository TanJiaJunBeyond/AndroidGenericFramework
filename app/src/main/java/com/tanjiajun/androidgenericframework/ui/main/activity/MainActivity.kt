package com.tanjiajun.androidgenericframework.ui.main.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.tanjiajun.androidgenericframework.EXTRA_LOGOUT
import com.tanjiajun.androidgenericframework.R
import com.tanjiajun.androidgenericframework.databinding.ActivityMainBinding
import com.tanjiajun.androidgenericframework.ui.BaseActivity
import com.tanjiajun.androidgenericframework.ui.main.viewModel.MainViewModel
import com.tanjiajun.androidgenericframework.ui.order.fragment.OrderFragment
import com.tanjiajun.androidgenericframework.ui.user.activity.PersonalCenterActivity
import com.tanjiajun.androidgenericframework.ui.user.activity.RegisterAndLoginActivity
import com.tanjiajun.androidgenericframework.utils.registerOnTabSelectedListener
import com.tanjiajun.androidgenericframework.utils.startActivity

/**
 * Created by TanJiaJun on 2019-07-22.
 */
class MainActivity
    : BaseActivity(), MainViewModel.Handlers {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    private val viewModel by lazy { ViewModelProviders.of(this)[MainViewModel::class.java] }

    private val tlOrder: TabLayout
        get() = binding.tlOrder

    private val vpOrder: ViewPager2
        get() = binding.vpOrder

    private val list = mutableListOf<OrderFragment>().apply {
        for (i in 0 until ORDER_ITEM_COUNT) {
            add(OrderFragment.newInstance(i))
        }
    }

    private val adapter by lazy { OrderFragmentStateAdapter(this@MainActivity, list) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.viewModel
            handlers = this@MainActivity
        }

        initUI()
    }

    private fun initUI() {
        for (i in 0 until ORDER_ITEM_COUNT) {
            tlOrder.addTab(tlOrder.newTab().setText("订单$i"))
        }

        tlOrder.addOnTabSelectedListener(registerOnTabSelectedListener {
            onTabSelected { vpOrder.currentItem = it?.position ?: 0 }
        })

        vpOrder.adapter = adapter
        vpOrder.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) =
                    tlOrder.setScrollPosition(position, 0f, true)
        })
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent
                ?.getBooleanExtra(EXTRA_LOGOUT, false)
                ?.takeIf { it }
                ?.let {
                    startActivity<RegisterAndLoginActivity>()
                    finish()
                }
    }

    override fun onPersonalCenterClick(view: View) =
            startActivity<PersonalCenterActivity>()

    override fun onStateClick(view: View) {
        if (viewModel.added.value == true) {
            tlOrder.removeTabAt(ORDER_ITEM_COUNT)
            list.removeAt(ORDER_ITEM_COUNT)
            adapter.notifyItemRemoved(ORDER_ITEM_COUNT)
            vpOrder.currentItem = 0
        } else {
            tlOrder.addTab(tlOrder.newTab().setText("订单$ORDER_ITEM_COUNT"))
            list.add(OrderFragment.newInstance(ORDER_ITEM_COUNT))
            adapter.notifyItemInserted(ORDER_ITEM_COUNT)
            vpOrder.currentItem = ORDER_ITEM_COUNT
        }

        viewModel.changeState()
    }

}

class OrderFragmentStateAdapter(fragmentActivity: FragmentActivity,
                                private val list: List<OrderFragment>)
    : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment =
            list[position]

    override fun getItemCount(): Int = list.size

}

const val ORDER_ITEM_COUNT = 4