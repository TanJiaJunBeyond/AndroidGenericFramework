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
abstract class BaseFragment<T : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    lateinit var binding: T

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            DataBindingUtil.inflate<T>(inflater, layoutRes, container, false)
                    .also { binding = it }
                    .root

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

    open fun onHandleGoBack() {
        // no implementation
    }

}