package com.tanjiajun.androidgenericframework.ui.main.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
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

    private val binding by lazy { DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.run {
            lifecycleOwner = this@MainActivity
            handlers = this@MainActivity
        }
    }

    override fun onResume() {
        super.onResume()
        initUI()
    }

    private fun initUI() =
            binding.run {
                for (i in 0 until ORDER_ITEM_COUNT) {
                    tlOrder.addTab(tlOrder.newTab().setText("订单$i"))
                }

                tlOrder.addOnTabSelectedListener(registerOnTabSelectedListener {
                    onTabSelected { vpOrder.currentItem = it?.position ?: 0 }
                })

                vpOrder.adapter = OrderFragmentStateAdaper(this@MainActivity)
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

}

class OrderFragmentStateAdaper(fragmentActivity: FragmentActivity)
    : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment =
            OrderFragment.newInstance(position)

    override fun getItemCount(): Int = ORDER_ITEM_COUNT

}

const val ORDER_ITEM_COUNT = 4