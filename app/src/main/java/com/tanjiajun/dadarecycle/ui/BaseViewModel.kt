package com.tanjiajun.dadarecycle.ui

import androidx.lifecycle.viewModelScope
import com.tanjiajun.dadarecycle.utils.ObservableViewModel
import kotlinx.coroutines.launch

/**
 * Created by TanJiaJun on 2019-08-02.
 */
abstract class BaseViewModel : ObservableViewModel() {

    protected fun launch(
            block: suspend () -> Unit,
            error: suspend (Throwable) -> Unit
    ) =
            viewModelScope.launch {
                try {
                    block()
                } catch (throwable: Throwable) {
                    error(throwable)
                }
            }

}