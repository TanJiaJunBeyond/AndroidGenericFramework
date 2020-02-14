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

    protected val _isShowLoadingView = MutableLiveData<Boolean>()
    val isShowLoadingView: LiveData<Boolean> = _isShowLoadingView

    protected val _isShowErrorView = MutableLiveData<Boolean>()
    val isShowErrorView: LiveData<Boolean> = _isShowErrorView

    val uiLiveEvent by lazy { UILiveEvent() }

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

    fun <T> launch(isShowLoadingProgressDialog: Boolean = false,
                   isShowLoadingView: Boolean = false,
                   isShowErrorToast: Boolean = false,
                   isShowErrorView: Boolean = false,
                   block: suspend CoroutineScope.() -> T,
                   success: suspend CoroutineScope.(T) -> Unit,
                   error: (CoroutineScope.(ResponseThrowable) -> Unit)? = null,
                   complete: suspend CoroutineScope.() -> Unit) {
        if (isShowLoadingProgressDialog) uiLiveEvent.showLoadingProgressDialog.call()
        if (isShowLoadingView) _isShowLoadingView.value = true
        launchUI {
            handle(
                    block = withContext(Dispatchers.IO) { block },
                    success = withContext(Dispatchers.Main) { success },
                    error = {
                        if (isShowErrorToast) uiLiveEvent.showToastEvent.postValue("${it.code}:${it.errorMessage}")
                        if (isShowErrorView) _isShowErrorView.postValue(true)
                        error?.invoke(this, it)
                    },
                    complete = {
                        if (isShowLoadingProgressDialog) uiLiveEvent.dismissLoadingProgressDialog.call()
                        if (isShowLoadingView) _isShowLoadingView.value = false
                        complete()
                    }
            )
        }
    }

    inner class UILiveEvent {

        val showToastEvent by lazy { SingleLiveEvent<String>() }
        val showLoadingProgressDialog by lazy { SingleLiveEvent<Boolean>() }
        val dismissLoadingProgressDialog by lazy { SingleLiveEvent<Boolean>() }
        val showSnackbarEvent by lazy { SingleLiveEvent<String>() }

    }

    interface Handlers {

        @JvmDefault
        fun onNavigationIconClick(view: View) {
            // no implementation
        }

        @JvmDefault
        fun onRetryClick(view: View) {
            // no implementation
        }

    }

}