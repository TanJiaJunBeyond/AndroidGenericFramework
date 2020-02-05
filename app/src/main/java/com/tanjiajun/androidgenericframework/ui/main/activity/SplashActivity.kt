package com.tanjiajun.androidgenericframework.ui.main.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.tanjiajun.androidgenericframework.R
import com.tanjiajun.androidgenericframework.databinding.ActivitySplashBinding
import com.tanjiajun.androidgenericframework.ui.BaseActivity
import com.tanjiajun.androidgenericframework.ui.main.viewModel.SplashViewModel
import com.tanjiajun.androidgenericframework.ui.user.activity.RegisterAndLoginActivity
import com.tanjiajun.androidgenericframework.utils.getViewModelFactory
import com.tanjiajun.androidgenericframework.utils.startActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by TanJiaJun on 2019-08-09.
 */
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override val layoutRes: Int = R.layout.activity_splash

    private val viewModel by viewModels<SplashViewModel> { getViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
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