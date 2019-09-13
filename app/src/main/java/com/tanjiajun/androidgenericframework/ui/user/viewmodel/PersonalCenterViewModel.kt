package com.tanjiajun.androidgenericframework.ui.user.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tanjiajun.androidgenericframework.data.repository.UserRepository
import com.tanjiajun.androidgenericframework.ui.BaseViewModel

/**
 * Created by TanJiaJun on 2019-08-24.
 */
class PersonalCenterViewModel(
        private val repository: UserRepository
) : BaseViewModel() {

    private val _headPortraitUrl = MutableLiveData<String>()
    val headPortraitUrl: LiveData<String> = _headPortraitUrl

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _gender = MutableLiveData<String>()
    val gender: LiveData<String> = _gender

    private val _age = MutableLiveData<String>()
    val age: LiveData<String> = _age

    fun showTitle(title: String) {
        _title.value = title
    }

    fun showInfo() =
            repository
                    .getUserInfo()
                    ?.run {
                        _headPortraitUrl.value = headPortraitUrl
                        _userName.value = userName
                        _gender.value = gender
                        _age.value = age.toString()
                    }

    fun logout() =
            repository.logout()

    interface Handlers : BaseViewModel.Handlers {

        fun onLogoutClick(view: View)

    }

}