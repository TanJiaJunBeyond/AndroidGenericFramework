package com.tanjiajun.dadarecycle.ui

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.tanjiajun.dadarecycle.R

/**
 * Created by TanJiaJun on 2019-07-28.
 */
abstract class BaseFragment : Fragment() {

    @LayoutRes
    abstract fun getLayoutResource(): Int

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

}