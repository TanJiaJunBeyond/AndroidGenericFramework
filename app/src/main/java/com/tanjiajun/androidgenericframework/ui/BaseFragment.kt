package com.tanjiajun.androidgenericframework.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.tanjiajun.androidgenericframework.R

/**
 * Created by TanJiaJun on 2019-07-28.
 */
abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    lateinit var binding: T

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            DataBindingUtil.inflate<T>(inflater, layoutRes, container, false)
                    .also { binding = it }
                    .root

    @get:LayoutRes
    abstract val layoutRes: Int

    abstract fun getTransactionTag(): String

    fun getEnterAnimation(): Int =
            R.anim.switch_in_right

    fun getExitAnimation(): Int =
            R.anim.switch_out_left

    fun getPopEnterAnimation(): Int =
            R.anim.switch_in_left

    fun getPopExitAnimation(): Int =
            R.anim.switch_out_right

    fun enableAnimation(): Boolean = true

    open fun onHandleGoBack() {
        // no implementation
    }

}