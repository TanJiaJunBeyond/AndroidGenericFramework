package com.tanjiajun.dadarecycle.ui.main.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.tanjiajun.dadarecycle.R
import com.tanjiajun.dadarecycle.databinding.ActivitySplashBinding
import com.tanjiajun.dadarecycle.ui.BaseActivity
import com.tanjiajun.dadarecycle.ui.main.viewModel.SplashViewModel
import com.tanjiajun.dadarecycle.ui.user.activity.RegisterAndLoginActivity
import com.tanjiajun.dadarecycle.utils.getViewModelFactory
import com.tanjiajun.dadarecycle.utils.startActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by TanJiaJun on 2019-08-09.
 */
class SplashActivity : BaseActivity() {

    private val viewModel by viewModels<SplashViewModel> { getViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivitySplashBinding>(
                this,
                R.layout.activity_splash
        ).apply { lifecycleOwner = this@SplashActivity }

        GlobalScope.launch {
            delay(1000)

            viewModel
                    .isLogin()
                    .takeIf { it }
                    ?.let {
                        startActivity<MainActivity>()
                    }
                    ?: startActivity<RegisterAndLoginActivity>()

            finish()
        }
    }

}