package com.tanjiajun.dadarecycle.user.ui.user.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.tanjiajun.dadarecycle.BaseActivity
import com.tanjiajun.dadarecycle.R
import com.tanjiajun.dadarecycle.databinding.ActivityRegisterAndLoginBinding
import com.tanjiajun.dadarecycle.user.ui.user.fragment.LoginFragment

/**
 * Created by TanJiaJun on 2019-07-28.
 */
class RegisterAndLoginActivity : BaseActivity() {

    override fun getContainId(): Int = R.id.fl_content

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityRegisterAndLoginBinding>(
            this,
            R.layout.activity_register_and_login
        )

        addFragment(LoginFragment.newInstance())
    }
}