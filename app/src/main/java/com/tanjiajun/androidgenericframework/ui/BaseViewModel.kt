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
private typealias CommonCallback = suspend CoroutineScope.() -> Unit

private typealias ErrorCallback = suspend CoroutineScope.(ResponseThrowable) -> Unit

abstract class BaseViewModel : ViewModel() {

    // 标题
    protected val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    // 是否显示加载中页面
    protected val _isShowLoadingView = MutableLiveData<Boolean>()
    val isShowLoadingView: LiveData<Boolean> = _isShowLoadingView

    // 是否显示失败页面
    protected val _isShowErrorView = MutableLiveData<Boolean>()
    val isShowErrorView: LiveData<Boolean> = _isShowErrorView

    // UI事件
    val uiLiveEvent by lazy { UILiveEvent() }

    fun launchUI(block: suspend CoroutineScope.() -> Unit) =
            viewModelScope.launch { block() }

    @FlowPreview
    fun <T> launchFlow(block: suspend () -> T): Flow<T> =
            flow { emit(block()) }

    /**
     * 处理逻辑
     *
     * @param block 请求块
     * @param success 成功回调
     * @param error 失败回调
     * @param complete 完成回调（成功或者失败都会回调）
     */
    private suspend fun <T> handle(block: suspend CoroutineScope.() -> T,
                                   success: suspend CoroutineScope.(T) -> Unit,
                                   error: ErrorCallback,
                                   complete: CommonCallback) =
            coroutineScope {
                try {
                    success(block())
                } catch (throwable: Throwable) {
                    error(ExceptionHandler.handleException(throwable))
                } finally {
                    complete()
                }
            }

    /**
     * 处理网络请求
     *
     * @param uiState 处理UI状态
     * @param block 请求块
     * @param success 成功回调
     * @param error 失败回调
     * @param complete 完成回调（成功或者失败都会回调）
     */
    fun <T> launch(uiState: UIState = UIState(),
                   block: suspend CoroutineScope.() -> T,
                   success: (suspend CoroutineScope.(T) -> Unit)? = null,
                   error: (ErrorCallback)? = null,
                   complete: (CommonCallback)? = null) =
            with(uiState) {
                if (isShowLoadingProgressBar) uiLiveEvent.showLoadingProgressBar.call()
                if (isShowLoadingView) _isShowLoadingView.value = true
                if (isShowErrorView) _isShowErrorView.value = false
                launchUI {
                    handle(
                            block = withContext(Dispatchers.IO) { block },
                            success = { withContext(Dispatchers.Main) { success?.invoke(this, it) } },
                            error = {
                                withContext(Dispatchers.Main) {
                                    if (isShowErrorToast) uiLiveEvent.showToastEvent.postValue("${it.code}:${it.errorMessage}")
                                    if (isShowErrorView) _isShowErrorView.value = true
                                    error?.invoke(this, it)
                                }
                            },
                            complete = {
                                withContext(Dispatchers.Main) {
                                    if (isShowLoadingProgressBar) uiLiveEvent.dismissLoadingProgressBar.call()
                                    if (isShowLoadingView) _isShowLoadingView.value = false
                                    complete?.invoke(this)
                                }
                            }
                    )
                }
            }

    inner class UILiveEvent {

        val showToastEvent by lazy { SingleLiveEvent<String>() }
        val showLoadingProgressBar by lazy { SingleLiveEvent<Boolean>() }
        val dismissLoadingProgressBar by lazy { SingleLiveEvent<Boolean>() }
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

/**
 * UI状态
 *
 * @param isShowLoadingProgressBar 是否显示加载中ProgressBar
 * @param isShowLoadingView 是否显示加载中页面
 * @param isShowErrorToast 是否弹出错误Toast
 * @param isShowErrorView 是否显示错误页面
 */
data class UIState(
        val isShowLoadingProgressBar: Boolean = false,
        val isShowLoadingView: Boolean = false,
        val isShowErrorToast: Boolean = false,
        val isShowErrorView: Boolean = false
)