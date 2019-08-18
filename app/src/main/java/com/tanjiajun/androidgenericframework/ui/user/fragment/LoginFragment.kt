package com.tanjiajun.androidgenericframework.ui.user.fragment

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.tanjiajun.androidgenericframework.AndroidGenericFrameworkApplication
import com.tanjiajun.androidgenericframework.FRAGMENT_TAG_LOGIN
import com.tanjiajun.androidgenericframework.R
import com.tanjiajun.androidgenericframework.databinding.FragmentLoginBinding
import com.tanjiajun.androidgenericframework.ui.BaseFragment
import com.tanjiajun.androidgenericframework.ui.main.activity.MainActivity
import com.tanjiajun.androidgenericframework.ui.user.viewModel.LoginViewModel
import com.tanjiajun.androidgenericframework.utils.getViewModelFactory
import com.tanjiajun.androidgenericframework.utils.startActivity

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
                        lifecycleOwner = this@LoginFragment
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
                        startActivity<MainActivity>()
                    }
                })

                errorMessage.observe(this@LoginFragment, Observer {
                    Toast.makeText(AndroidGenericFrameworkApplication.context, it, Toast.LENGTH_SHORT).show()
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