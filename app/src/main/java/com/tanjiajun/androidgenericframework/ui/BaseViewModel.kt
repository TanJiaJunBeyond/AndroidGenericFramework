package com.tanjiajun.androidgenericframework.ui

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Created by TanJiaJun on 2019-08-02.
 */
abstract class BaseViewModel : ViewModel() {

    protected val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    protected fun launch(block: suspend () -> Unit,
                         error: suspend (Throwable) -> Unit) =
            viewModelScope.launch {
                try {
                    block()
                } catch (throwable: Throwable) {
                    error(throwable)
                }
            }

    interface Handlers {

        fun onNavigationIconClick(view: View)

    }

}