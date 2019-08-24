package com.tanjiajun.androidgenericframework.ui.main.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.tanjiajun.androidgenericframework.EXTRA_LOGOUT
import com.tanjiajun.androidgenericframework.R
import com.tanjiajun.androidgenericframework.databinding.ActivityMainBinding
import com.tanjiajun.androidgenericframework.ui.BaseActivity
import com.tanjiajun.androidgenericframework.ui.main.viewModel.MainViewModel
import com.tanjiajun.androidgenericframework.ui.user.activity.PersonalCenterActivity
import com.tanjiajun.androidgenericframework.ui.user.activity.RegisterAndLoginActivity
import com.tanjiajun.androidgenericframework.utils.startActivity

/**
 * Created by TanJiaJun on 2019-07-22.
 */
class MainActivity
    : BaseActivity(), MainViewModel.Handlers {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            lifecycleOwner = this@MainActivity
            handlers = this@MainActivity
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent
                ?.getBooleanExtra(EXTRA_LOGOUT, false)
                ?.takeIf { it }
                ?.let { startActivity<RegisterAndLoginActivity>() }
    }

    override fun onPersonalCenterClick(view: View) =
            startActivity<PersonalCenterActivity>()

}
