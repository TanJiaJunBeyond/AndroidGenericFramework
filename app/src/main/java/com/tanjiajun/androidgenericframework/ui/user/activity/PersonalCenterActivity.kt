package com.tanjiajun.androidgenericframework.ui.user.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.tanjiajun.androidgenericframework.EXTRA_LOGOUT
import com.tanjiajun.androidgenericframework.R
import com.tanjiajun.androidgenericframework.databinding.ActivityPersonalCenterBinding
import com.tanjiajun.androidgenericframework.ui.BaseActivity
import com.tanjiajun.androidgenericframework.ui.main.activity.MainActivity
import com.tanjiajun.androidgenericframework.ui.user.viewmodel.PersonalCenterViewModel
import com.tanjiajun.androidgenericframework.utils.getViewModelFactory

/**
 * Created by TanJiaJun on 2019-08-24.
 */
class PersonalCenterActivity
    : BaseActivity<ActivityPersonalCenterBinding, PersonalCenterViewModel>(), PersonalCenterViewModel.Handlers {

    override val layoutRes: Int = R.layout.activity_personal_center
    override val viewModel by viewModels<PersonalCenterViewModel> { getViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            lifecycleOwner = this@PersonalCenterActivity
            viewModel = this@PersonalCenterActivity.viewModel
            handlers = this@PersonalCenterActivity
        }
        viewModel.showTitle(getString(R.string.personal_center))
    }

    override fun onNavigationIconClick(view: View) =
            finish()

    override fun onLogoutClick(view: View) {
        viewModel.logout()
        startActivity(Intent(this, MainActivity::class.java).apply {
            putExtra(EXTRA_LOGOUT, true)
        })
    }

}