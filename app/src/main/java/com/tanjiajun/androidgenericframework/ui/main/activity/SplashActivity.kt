package com.tanjiajun.androidgenericframework.ui.main.activity

import android.os.Bundle
import com.tanjiajun.androidgenericframework.R
import com.tanjiajun.androidgenericframework.databinding.ActivitySplashBinding
import com.tanjiajun.androidgenericframework.ui.BaseActivity
import com.tanjiajun.androidgenericframework.ui.main.viewmodel.SplashViewModel
import com.tanjiajun.androidgenericframework.ui.user.activity.RegisterAndLoginActivity
import com.tanjiajun.androidgenericframework.utils.startActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

/**
 * Created by TanJiaJun on 2019-08-09.
 */
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    override val layoutRes: Int = R.layout.activity_splash
    override val viewModel by lifecycleScope.viewModel<SplashViewModel>(this)

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