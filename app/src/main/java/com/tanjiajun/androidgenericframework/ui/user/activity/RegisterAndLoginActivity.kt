package com.tanjiajun.androidgenericframework.ui.user.activity

import android.os.Bundle
import com.tanjiajun.androidgenericframework.R
import com.tanjiajun.androidgenericframework.databinding.ActivityRegisterAndLoginBinding
import com.tanjiajun.androidgenericframework.ui.BaseActivity
import com.tanjiajun.androidgenericframework.ui.NoViewModel
import com.tanjiajun.androidgenericframework.ui.user.fragment.LoginFragment

/**
 * Created by TanJiaJun on 2019-07-28.
 */
class RegisterAndLoginActivity : BaseActivity<ActivityRegisterAndLoginBinding, NoViewModel>() {

    override val layoutRes: Int = R.layout.activity_register_and_login
    override val viewModel = NoViewModel()
    override val containId: Int = R.id.fl_content

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFragment(LoginFragment.newInstance())
    }

}