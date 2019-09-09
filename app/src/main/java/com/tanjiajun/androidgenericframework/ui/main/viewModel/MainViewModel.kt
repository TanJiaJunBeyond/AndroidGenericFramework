package com.tanjiajun.androidgenericframework.ui.main.viewModel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tanjiajun.androidgenericframework.ui.BaseViewModel

/**
 * Created by TanJiaJun on 2019-08-24.
 */
class MainViewModel : BaseViewModel() {

    private val _added = MutableLiveData<Boolean>()
    val added: LiveData<Boolean> = _added

    fun changeState() {
        _added.value = !(_added.value ?: false)
    }

    interface Handlers {

        fun onPersonalCenterClick(view: View)

        fun onStateClick(view: View)

    }

}