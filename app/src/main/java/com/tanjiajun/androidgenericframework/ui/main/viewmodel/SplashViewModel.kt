package com.tanjiajun.androidgenericframework.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tanjiajun.androidgenericframework.data.repository.UserInfoRepository
import com.tanjiajun.androidgenericframework.ui.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by TanJiaJun on 2019-08-12.
 */
class SplashViewModel @Inject constructor(
        private val repository: UserInfoRepository
) : BaseViewModel() {

    private val _isNavigateToMainActivity = MutableLiveData<Boolean>()
    var isNavigateToMainActivity: LiveData<Boolean> = _isNavigateToMainActivity

    fun navigateToPage() =
            viewModelScope.launch {
                delay(1000)
                _isNavigateToMainActivity.value = repository.isLogin()
            }

}