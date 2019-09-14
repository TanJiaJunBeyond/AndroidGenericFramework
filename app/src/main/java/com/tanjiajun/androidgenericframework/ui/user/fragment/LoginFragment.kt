package com.tanjiajun.androidgenericframework.ui.user.fragment

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.tanjiajun.androidgenericframework.FRAGMENT_TAG_LOGIN
import com.tanjiajun.androidgenericframework.R
import com.tanjiajun.androidgenericframework.databinding.FragmentLoginBinding
import com.tanjiajun.androidgenericframework.ui.BaseFragment
import com.tanjiajun.androidgenericframework.ui.main.activity.MainActivity
import com.tanjiajun.androidgenericframework.ui.user.viewmodel.LoginViewModel
import com.tanjiajun.androidgenericframework.utils.getViewModelFactory
import com.tanjiajun.androidgenericframework.utils.startActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by TanJiaJun on 2019-07-29.
 */
class LoginFragment
    : BaseFragment(),
        LoginViewModel.Handlers {

    private val viewModel by viewModels<LoginViewModel> { getViewModelFactory() }

    override fun getLayoutResource(): Int = R.layout.fragment_login

    override fun getTransactionTag(): String = FRAGMENT_TAG_LOGIN

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            DataBindingUtil.inflate<FragmentLoginBinding>(inflater, getLayoutResource(), container, false)
                    .apply {
                        lifecycleOwner = this@LoginFragment
                        viewModel = this@LoginFragment.viewModel
                        handlers = this@LoginFragment
                    }
                    .also { observe() }
                    .root

    private fun observe() =
            with(viewModel) {
                loginSuccess.observe(this@LoginFragment, Observer {
                    GlobalScope.launch {
                        delay(3000)

                        if (it) {
                            startActivity<MainActivity>()
                            activity?.finish()
                        }
                    }
                })

                errorMessage.observe(this@LoginFragment, Observer { message ->
                    view?.let { view ->
                        context?.let { context ->
                            Snackbar
                                    .make(view, message, Snackbar.LENGTH_LONG)
                                    .setActionTextColor(ContextCompat.getColor(context, R.color.main_color))
                                    .setAction(R.string.cancel) {
                                        Toast.makeText(context, R.string.cancel, Toast.LENGTH_LONG).show()
                                    }
                                    .show()
                        }
                    }
                })
            }

    override fun onPhoneNumberAfterTextChanged(editable: Editable) =
            viewModel.checkLoginEnable()

    override fun onPasswordAfterTextChanged(editable: Editable) =
            viewModel.checkLoginEnable()

    override fun onLoginClick(view: View) {
        viewModel.login()
    }

    companion object {
        fun newInstance() = LoginFragment()
    }

}