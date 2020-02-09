package com.tanjiajun.androidgenericframework.ui.user.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tanjiajun.androidgenericframework.data.repository.UserInfoRepository
import com.tanjiajun.androidgenericframework.ui.BaseViewModel

/**
 * Created by TanJiaJun on 2019-08-24.
 */
class PersonalCenterViewModel(
        private val repository: UserInfoRepository
) : BaseViewModel() {

    private val _avatarUrl = MutableLiveData<String>().apply {
        value = repository.getAvatarUrl()
    }
    val avatarUrl: LiveData<String> = _avatarUrl

    private val _name = MutableLiveData<String>().apply {
        value = repository.getName()
    }
    val name: LiveData<String> = _name

    fun showTitle(title: String) {
        _title.value = title
    }

    fun logout() =
            repository.logout()

    interface Handlers : BaseViewModel.Handlers {

        fun onLogoutClick(view: View)

    }

}