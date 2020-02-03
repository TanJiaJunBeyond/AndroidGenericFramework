package com.tanjiajun.androidgenericframework.ui

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanjiajun.androidgenericframework.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

/**
 * Created by TanJiaJun on 2019-08-02.
 */
abstract class BaseViewModel : ViewModel() {

    protected val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    val defaultUI by lazy { UIChange() }

    fun launchUI(block: suspend CoroutineScope.() -> Unit) =
            viewModelScope.launch { block() }

    @FlowPreview
    fun <T> launchFlow(block: suspend () -> T): Flow<T> =
            flow { emit(block()) }

    protected fun launch(block: suspend () -> Unit,
                         error: suspend (Throwable) -> Unit) =
            viewModelScope.launch {
                try {
                    block()
                } catch (throwable: Throwable) {
                    error(throwable)
                }
            }

    inner class UIChange {
        val toastEvent by lazy { SingleLiveEvent<String>() }
        val showDialogEvent by lazy { SingleLiveEvent<Boolean>() }
        val dismissDialogEvent by lazy { SingleLiveEvent<Boolean>() }
    }

    interface Handlers {

        fun onNavigationIconClick(view: View)

    }

}