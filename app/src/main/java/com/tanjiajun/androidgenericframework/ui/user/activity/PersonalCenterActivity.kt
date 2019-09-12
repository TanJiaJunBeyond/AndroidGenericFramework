package com.tanjiajun.androidgenericframework.ui.user.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.tanjiajun.androidgenericframework.EXTRA_LOGOUT
import com.tanjiajun.androidgenericframework.R
import com.tanjiajun.androidgenericframework.databinding.ActivityPersonalCenterBinding
import com.tanjiajun.androidgenericframework.ui.BaseActivity
import com.tanjiajun.androidgenericframework.ui.main.activity.MainActivity
import com.tanjiajun.androidgenericframework.ui.user.viewModel.PersonalCenterViewModel
import com.tanjiajun.androidgenericframework.utils.getViewModelFactory

/**
 * Created by TanJiaJun on 2019-08-24.
 */
class PersonalCenterActivity
    : BaseActivity(), PersonalCenterViewModel.Handlers {

    private val viewModel by viewModels<PersonalCenterViewModel> { getViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityPersonalCenterBinding>(
                this,
                R.layout.activity_personal_center
        ).apply {
            lifecycleOwner = this@PersonalCenterActivity
            viewModel = this@PersonalCenterActivity.viewModel
            handlers = this@PersonalCenterActivity
        }
    }

    override fun onResume() {
        super.onResume()
        with(viewModel) {
            showTitle(getString(R.string.personal_center))
            showInfo()
        }
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