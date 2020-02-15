package com.tanjiajun.androidgenericframework.ui.user.viewmodel

import android.text.Editable
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tanjiajun.androidgenericframework.data.network.ExceptionHandler
import com.tanjiajun.androidgenericframework.data.repository.UserInfoRepository
import com.tanjiajun.androidgenericframework.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

/**
 * Created by TanJiaJun on 2019-08-02.
 */
class LoginViewModel(
        private val repository: UserInfoRepository
) : BaseViewModel() {

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    val loginSuccess = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun checkLoginEnable() {
        _loginEnable.value = !username.value.isNullOrEmpty() && !password.value.isNullOrEmpty()
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun login() =
            launchUI {
                uiLiveEvent.showLoadingProgressBar.call()
                launchFlow {
                    repository.run {
                        cacheUsername(username.value ?: "")
                        cachePassword(password.value ?: "")
                        authorizations()
                    }
                }
                        .flatMapMerge {
                            launchFlow { repository.getUserInfo() }
                        }
                        .flowOn(Dispatchers.IO)
                        .onStart { _isShowLoadingView.value = true }
                        .catch {
                            val responseThrowable = ExceptionHandler.handleException(it)
                            uiLiveEvent.showSnackbarEvent.value = "${responseThrowable.code}:${responseThrowable.errorMessage}"
                            loginSuccess.value = false
                        }
                        .onCompletion { uiLiveEvent.dismissLoadingProgressBar.call() }
                        .collect {
                            repository.run {
                                cacheUserId(it.id)
                                cacheName(it.login)
                                cacheAvatarUrl(it.avatarUrl)
                            }
                            loginSuccess.value = true
                        }
            }

    interface Handlers {

        fun onUsernameAfterTextChanged(editable: Editable)

        fun onPasswordAfterTextChanged(editable: Editable)

        fun onLoginClick(view: View)

    }

}