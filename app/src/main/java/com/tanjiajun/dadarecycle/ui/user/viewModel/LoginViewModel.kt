package com.tanjiajun.dadarecycle.ui.user.viewModel

import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tanjiajun.dadarecycle.DaDaRecycleApplication
import com.tanjiajun.dadarecycle.data.model.BaseViewModel
import com.tanjiajun.dadarecycle.data.repository.UserRepository

/**
 * Created by TanJiaJun on 2019-08-02.
 */
class LoginViewModel(private val repository: UserRepository) : BaseViewModel() {

    val phoneNumber = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    private val _isLoginClickable = MutableLiveData<Boolean>()
    val isLoginClickable: LiveData<Boolean> = _isLoginClickable

    fun checkLoginEnable() {
        _isLoginClickable.value = !phoneNumber.value.isNullOrEmpty() && !password.value.isNullOrEmpty()
    }

    fun login() =
            launch({
                val phoneNumber = phoneNumber.value
                val password = password.value

                if (!phoneNumber.isNullOrEmpty() && !password.isNullOrEmpty()) {
                    repository.login(phoneNumber, password)
                }
            }, {
                Toast.makeText(DaDaRecycleApplication.context, it.message, Toast.LENGTH_SHORT).show()
            })

    interface Handlers {

        fun onLoginClick(view: View)

    }

}