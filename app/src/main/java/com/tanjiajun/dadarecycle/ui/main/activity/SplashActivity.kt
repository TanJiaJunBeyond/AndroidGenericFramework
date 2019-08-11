package com.tanjiajun.dadarecycle.ui.main.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.tanjiajun.dadarecycle.R
import com.tanjiajun.dadarecycle.databinding.ActivitySplashBinding
import com.tanjiajun.dadarecycle.ui.BaseActivity

/**
 * Created by TanJiaJun on 2019-08-09.
 */
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivitySplashBinding>(
                this,
                R.layout.activity_splash
        )
    }

}