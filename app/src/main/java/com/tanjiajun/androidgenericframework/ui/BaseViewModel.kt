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

    fun <T> launch(isShowDialog: Boolean,
                   block: suspend CoroutineScope.() -> T,
                   success: (T) -> Unit,
                   error: CoroutineScope.(ResponseThrowable) -> Unit = {
                       defaultUI.showToastEvent.postValue("${it.code}:${it.errorMessage}")
                   },
                   complete: suspend CoroutineScope.() -> Unit) {
        if (isShowDialog) defaultUI.showDialogEvent.call()
        launchUI {
            handle(
                    block = withContext(Dispatchers.IO) { block },
                    success = { coroutineScope { success(it) } },
                    error = { error(it) },
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
        val showDialogEvent by lazy { SingleLiveEvent<Boolean>() }
        val dismissDialogEvent by lazy { SingleLiveEvent<Boolean>() }

    }

    interface Handlers {

        fun onNavigationIconClick(view: View)

    }

}