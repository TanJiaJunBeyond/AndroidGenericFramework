package com.tanjiajun.androidgenericframework.ui.user.viewModel

import android.text.Editable
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tanjiajun.androidgenericframework.data.repository.UserRepository
import com.tanjiajun.androidgenericframework.ui.BaseViewModel

/**
 * Created by TanJiaJun on 2019-08-02.
 */
class LoginViewModel(
        private val repository: UserRepository
) : BaseViewModel() {

    val phoneNumber = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    val loginSuccess = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun checkLoginEnable() {
        _loginEnable.value = !phoneNumber.value.isNullOrEmpty() && !password.value.isNullOrEmpty()
    }

    fun login() =
            launch({
                val phoneNumber = phoneNumber.value
                val password = password.value

                if (!phoneNumber.isNullOrEmpty() && !password.isNullOrEmpty()) {
                    repository.login(phoneNumber, password).let {
                        loginSuccess.value = true
                    }
                }
            }, {
                // TODO 因为后端原因，暂时当作登录成功
                loginSuccess.value = true
//                loginSuccess.value = false
//                errorMessage.value = it.message
            })

    interface Handlers {

        fun onPhoneNumberAfterTextChanged(editable: Editable)

        fun onPasswordAfterTextChanged(editable: Editable)

        fun onLoginClick(view: View)

    }

}