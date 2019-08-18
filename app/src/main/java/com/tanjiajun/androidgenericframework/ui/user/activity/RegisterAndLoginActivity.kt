package com.tanjiajun.androidgenericframework.ui.user.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.tanjiajun.androidgenericframework.R
import com.tanjiajun.androidgenericframework.databinding.ActivityRegisterAndLoginBinding
import com.tanjiajun.androidgenericframework.ui.BaseActivity
import com.tanjiajun.androidgenericframework.ui.user.fragment.LoginFragment

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