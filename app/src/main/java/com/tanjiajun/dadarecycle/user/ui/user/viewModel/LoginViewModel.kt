package com.tanjiajun.dadarecycle.user.ui.user.viewModel

import androidx.lifecycle.MutableLiveData
import com.tanjiajun.dadarecycle.user.data.model.BaseViewModel
import com.tanjiajun.dadarecycle.user.data.model.response.UserInfoData

/**
 * Created by TanJiaJun on 2019-08-02.
 */
class LoginViewModel : BaseViewModel() {

    var userInfoData = MutableLiveData<UserInfoData>()

}