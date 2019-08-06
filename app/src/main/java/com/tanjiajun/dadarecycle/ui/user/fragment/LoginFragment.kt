package com.tanjiajun.dadarecycle.ui.user.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.tanjiajun.dadarecycle.FRAGMENT_TAG_LOGIN
import com.tanjiajun.dadarecycle.R
import com.tanjiajun.dadarecycle.databinding.FragmentLoginBinding
import com.tanjiajun.dadarecycle.ui.BaseFragment
import com.tanjiajun.dadarecycle.ui.user.viewModel.LoginViewModel
import com.tanjiajun.dadarecycle.utils.getViewModelFactory

/**
 * Created by TanJiaJun on 2019-07-29.
 */
class LoginFragment
    : BaseFragment(),
    LoginViewModel.Handlers {

    private val viewModel by viewModels<LoginViewModel> { getViewModelFactory() }

    override fun getLayoutResource(): Int = R.layout.fragment_login

    override fun getTransactionTag(): String = FRAGMENT_TAG_LOGIN

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        DataBindingUtil.inflate<FragmentLoginBinding>(inflater, getLayoutResource(), container, false)
            .apply {
                viewModel = this@LoginFragment.viewModel
                handlers = this@LoginFragment
            }
            .root

    override fun onLoginClick(view: View) {
        viewModel.login()
    }

    companion object {
        fun newInstance() =
            LoginFragment()
    }

}