package com.tanjiajun.androidgenericframework.ui.user.fragment

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
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
import kotlinx.coroutines.*

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
            }.also { observe() }

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