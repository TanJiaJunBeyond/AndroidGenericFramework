package com.tanjiajun.dadarecycle.user.ui.user.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.tanjiajun.dadarecycle.BaseFragment
import com.tanjiajun.dadarecycle.FRAGMENT_TAG_LOGIN
import com.tanjiajun.dadarecycle.R
import com.tanjiajun.dadarecycle.databinding.FragmentLoginBinding
import com.tanjiajun.dadarecycle.user.ui.user.viewModel.LoginViewModel

/**
 * Created by TanJiaJun on 2019-07-29.
 */
class LoginFragment
    : BaseFragment(),
        LoginViewModel.Handlers {

    override fun getLayoutResource(): Int = R.layout.fragment_login

    override fun getTransactionTag(): String = FRAGMENT_TAG_LOGIN

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            DataBindingUtil.inflate<FragmentLoginBinding>(inflater, getLayoutResource(), container, false).root

    override fun onLoginClick(view: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun newInstance() =
                LoginFragment()
    }

}