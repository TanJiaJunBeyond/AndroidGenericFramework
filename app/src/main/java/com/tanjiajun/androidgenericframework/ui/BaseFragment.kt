package com.tanjiajun.androidgenericframework.ui

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.tanjiajun.androidgenericframework.R
import com.tanjiajun.androidgenericframework.utils.toastShort

/**
 * Created by TanJiaJun on 2019-07-28.
 */
abstract class BaseFragment<T : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    lateinit var binding: T

    @get:LayoutRes
    abstract val layoutRes: Int

    abstract val viewModel: VM

    abstract val transactionTag: String

    open val enterAnimation: Int =
            R.anim.switch_in_right

    open val exitAnimation: Int =
            R.anim.switch_out_left

    open val popEnterAnimation: Int =
            R.anim.switch_in_left

    open val popExitAnimation: Int =
            R.anim.switch_out_right

    open val enableAnimation: Boolean = true

    private var progressBar: ProgressBar? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            DataBindingUtil.inflate<T>(inflater, layoutRes, container, false)
                    .also { binding = it }
                    .root

    protected fun registerToastEvent() =
            viewModel.uiLiveEvent.showToastEvent.observe(this, Observer {
                toastShort(it)
            })

    protected fun registerLoadingProgressBarEvent() =
            with(viewModel.uiLiveEvent) {
                showLoadingProgressBarEvent.observe(this@BaseFragment, Observer {
                    activity?.findViewById<FrameLayout>(android.R.id.content)?.addView(
                            ProgressBar(context)
                                    .apply {
                                        layoutParams = FrameLayout.LayoutParams(
                                                FrameLayout.LayoutParams.WRAP_CONTENT,
                                                FrameLayout.LayoutParams.WRAP_CONTENT
                                        ).also { it.gravity = Gravity.CENTER }
                                    }
                                    .also { progressBar = it }
                    )
                })
                dismissLoadingProgressBarEvent.observe(this@BaseFragment, Observer {
                    progressBar?.let { activity?.findViewById<FrameLayout>(android.R.id.content)?.removeView(it) }
                })
            }

    protected fun registerSnackbarEvent() =
            viewModel.uiLiveEvent.showSnackbarEvent.observe(this, Observer {
                activity?.let { activity ->
                    context?.let { context ->
                        Snackbar
                                .make(activity.window.decorView, it, Snackbar.LENGTH_SHORT)
                                .setActionTextColor(ContextCompat.getColor(context, R.color.white))
                                .show()
                    }
                }
            })

    open fun onHandleGoBack() {
        // no implementation
    }

}