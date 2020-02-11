package com.tanjiajun.androidgenericframework.ui

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanjiajun.androidgenericframework.data.network.ExceptionHandler
import com.tanjiajun.androidgenericframework.data.network.ResponseThrowable
import com.tanjiajun.androidgenericframework.utils.SingleLiveEvent
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by TanJiaJun on 2019-08-02.
 */
abstract class BaseViewModel : ViewModel() {

    protected val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _isShowLoadingView = MutableLiveData<Boolean>()
    val isShowLoadingView: LiveData<Boolean> = _isShowLoadingView

    private val _isShowErrorView = MutableLiveData<Boolean>()
    val isShowErrorView: LiveData<Boolean> = _isShowErrorView

    val defaultUI by lazy { UIChange() }

    fun launchUI(block: suspend CoroutineScope.() -> Unit) =
            viewModelScope.launch { block() }

    @FlowPreview
    fun <T> launchFlow(block: suspend () -> T): Flow<T> =
            flow { emit(block()) }

    private suspend fun <T> handle(block: suspend CoroutineScope.() -> T,
                                   success: suspend CoroutineScope.(T) -> Unit,
                                   error: suspend CoroutineScope.(ResponseThrowable) -> Unit,
                                   complete: suspend CoroutineScope.() -> Unit) =
            coroutineScope {
                try {
                    success(block())
                } catch (throwable: Throwable) {
                    error(ExceptionHandler.handleException(throwable))
                } finally {
                    complete()
                }
            }

    fun <T> launch(isShowDialog: Boolean = false,
                   isShowErrorToast: Boolean = false,
                   isShowErrorView: Boolean = false,
                   block: suspend CoroutineScope.() -> T,
                   success: suspend CoroutineScope.(T) -> Unit,
                   error: (CoroutineScope.(ResponseThrowable) -> Unit)? = null,
                   complete: suspend CoroutineScope.() -> Unit) {
        if (isShowDialog) _isShowLoadingView.value = true
        launchUI {
            handle(
                    block = withContext(Dispatchers.IO) { block },
                    success = withContext(Dispatchers.Main) { success },
                    error = {
                        if (isShowErrorToast) defaultUI.showToastEvent.postValue("${it.code}:${it.errorMessage}")
                        if (isShowErrorView) _isShowErrorView.postValue(true)
                        error?.invoke(this, it)
                    },
                    complete = {
                        defaultUI.dismissDialogEvent.call()
                        complete()
                    }
            )
        }
    }

    inner class UIChange {

        val showToastEvent by lazy { SingleLiveEvent<String>() }
        val showSnackbar by lazy { SingleLiveEvent<String>() }
        val dismissDialogEvent by lazy { SingleLiveEvent<Boolean>() }

    }

    interface Handlers {

        fun onNavigationIconClick(view: View)

    }

}