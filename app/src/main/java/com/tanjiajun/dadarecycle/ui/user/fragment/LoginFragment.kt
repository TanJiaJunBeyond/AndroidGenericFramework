package com.tanjiajun.dadarecycle.ui.user.fragment

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.tanjiajun.dadarecycle.DaDaRecycleApplication
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

    override fun onResume() {
        super.onResume()
        observe()
    }

    private fun observe() =
            viewModel.run {
                loginSuccess.observe(this@LoginFragment, Observer {
                    if (it) {
                        Toast.makeText(DaDaRecycleApplication.context, "登录成功", Toast.LENGTH_SHORT).show()
                    }
                })

                errorMessage.observe(this@LoginFragment, Observer {
                    Toast.makeText(DaDaRecycleApplication.context, it, Toast.LENGTH_SHORT).show()
                })
            }

    override fun onPhoneNumberAfterTextChanged(editable: Editable) {
        viewModel.checkLoginEnable()
    }

    override fun onPasswordAfterTextChanged(editable: Editable) {
        viewModel.checkLoginEnable()
    }

    override fun onLoginClick(view: View) {
        viewModel.login()
    }

    companion object {
        fun newInstance() =
                LoginFragment()
    }

}