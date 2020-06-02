package com.tanjiajun.androidgenericframework.ui.main.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import com.tanjiajun.androidgenericframework.R
import com.tanjiajun.androidgenericframework.databinding.ActivitySplashBinding
import com.tanjiajun.androidgenericframework.ui.BaseActivity
import com.tanjiajun.androidgenericframework.ui.main.viewmodel.SplashViewModel
import com.tanjiajun.androidgenericframework.ui.user.activity.RegisterAndLoginActivity
import com.tanjiajun.androidgenericframework.utils.otherwise
import com.tanjiajun.androidgenericframework.utils.startActivity
import com.tanjiajun.androidgenericframework.utils.yes
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
        with(viewModel) {
            navigateToPage()
            isNavigateToMainActivity.observe(this@SplashActivity, Observer {
                it
                        .yes { startActivity<MainActivity>() }
                        .otherwise { startActivity<RegisterAndLoginActivity>() }
                finish()
            })
        }
    }

}