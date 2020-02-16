package com.tanjiajun.androidgenericframework.ui.user.fragment

import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.tanjiajun.androidgenericframework.FRAGMENT_TAG_LOGIN
import com.tanjiajun.androidgenericframework.R
import com.tanjiajun.androidgenericframework.databinding.FragmentLoginBinding
import com.tanjiajun.androidgenericframework.ui.BaseFragment
import com.tanjiajun.androidgenericframework.ui.main.activity.MainActivity
import com.tanjiajun.androidgenericframework.ui.user.viewmodel.LoginViewModel
import com.tanjiajun.androidgenericframework.utils.getViewModelFactory
import com.tanjiajun.androidgenericframework.utils.startActivity
import com.tanjiajun.androidgenericframework.utils.yes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

/**
 * Created by TanJiaJun on 2019-07-29.
 */
class LoginFragment private constructor()
    : BaseFragment<FragmentLoginBinding, LoginViewModel>(),
        LoginViewModel.Handlers {

    override val layoutRes: Int = R.layout.fragment_login
    override val viewModel by viewModels<LoginViewModel> { getViewModelFactory() }
    override val transactionTag: String = FRAGMENT_TAG_LOGIN

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) =
            with(binding) {
                lifecycleOwner = this@LoginFragment
                viewModel = this@LoginFragment.viewModel
                handlers = this@LoginFragment
            }.also {
                registerLoadingProgressBarEvent()
                registerSnackbarEvent()
                observe()
            }

    private fun observe() =
            viewModel.isLoginSuccess.observe(this@LoginFragment, Observer {
                it.yes {
                    startActivity<MainActivity>()
                    activity?.finish()
                }
            })

    override fun onUsernameAfterTextChanged(editable: Editable) =
            viewModel.checkLoginEnable()

    override fun onPasswordAfterTextChanged(editable: Editable) =
            viewModel.checkLoginEnable()

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun onLoginClick(view: View) {
        viewModel.login()
    }

    companion object {
        fun newInstance() = LoginFragment()
    }

}