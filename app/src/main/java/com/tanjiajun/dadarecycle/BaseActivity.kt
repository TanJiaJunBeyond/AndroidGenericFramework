package com.tanjiajun.dadarecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Created by TanJiaJun on 2019-07-28.
 */
abstract class BaseActivity : AppCompatActivity() {

    private lateinit var manager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manager = supportFragmentManager
    }

    open fun getContainId(): Int = -1

    fun getCurrentFragment(): BaseFragment =
        manager.findFragmentById(getContainId()) as BaseFragment

    fun getFragmentList(): List<Fragment?> =
        mutableListOf<Fragment?>().apply {
            val count = manager.backStackEntryCount

            for (i in 0 until count) {
                add(manager.findFragmentByTag(manager.getBackStackEntryAt(i).name))
            }
        }

    fun getPreviousFragment(): BaseFragment? =
        manager.backStackEntryCount
            .takeIf { it >= 2 }
            ?.let {
                manager.findFragmentByTag(manager.getBackStackEntryAt(it.minus(2)).name) as BaseFragment
            }

    fun popBackStackImmediate(): Boolean =
        manager.run {
            if (backStackEntryCount < 1) {
                return@run false
            }

            try {
                manager.popBackStackImmediate()
            } catch (exception: IllegalStateException) {
                return@run false
            }
        }

    fun popBackStackImmediate(name: String, flags: Int): Boolean =
        manager.run {
            if (backStackEntryCount < 1) {
                return@run false
            }

            try {
                manager.popBackStackImmediate(name, flags)
            } catch (exception: IllegalStateException) {
                return@run false
            }
        }

    fun addFragment(fragment: BaseFragment) =
        operateFragmentTransaction(fragment, ACTION_ADD, true, true)

    fun addFragmentAndHideOthers(fragment: BaseFragment) =
        operateFragmentTransaction(fragment, ACTION_ADD_AND_HIDE_OTHERS, true, true)

    fun addFragmentAndHideOthersNotExecutePending(fragment: BaseFragment) =
        operateFragmentTransaction(fragment, ACTION_ADD_AND_HIDE_OTHERS, false, true)

    fun replaceFragment(fragment: BaseFragment) =
        replaceFragment(fragment, true)

    fun replaceFragment(fragment: BaseFragment, addToBackStack: Boolean) =
        operateFragmentTransaction(fragment, ACTION_REPLACE, true, addToBackStack)

    protected fun addFragmentNotAddToBackStack(fragment: BaseFragment) =
        operateFragmentTransaction(fragment, ACTION_ADD, false, false)

    fun addFragmentNotExecutePending(fragment: BaseFragment) =
        operateFragmentTransaction(fragment, ACTION_ADD, false, true)

    private fun operateFragmentTransaction(
        fragment: BaseFragment,
        action: Int,
        isExecutePending: Boolean,
        isAddToBackStack: Boolean
    ) =
        manager.run {
            val containId = getContainId()

            if (containId > 0) {
                beginTransaction().run {
                    if (fragment.enableAnimation()) {
                        setCustomAnimations(
                            fragment.getEnterAnimation(),
                            fragment.getExitAnimation(),
                            fragment.getPopEnterAnimation(),
                            fragment.getPopExitAnimation()
                        )
                    }

                    when (action) {
                        ACTION_ADD -> add(containId, fragment, fragment.getTransactionTag())
                        ACTION_REPLACE -> replace(containId, fragment, fragment.getTransactionTag())
                        ACTION_ADD_AND_HIDE_OTHERS -> {
                            manager.fragments
                                .filter { it.isVisible }
                                .forEach { hide(it) }

                            add(containId, fragment, fragment.getTransactionTag())
                        }
                        else -> Unit
                    }

                    if (isAddToBackStack) {
                        addToBackStack(fragment.getTransactionTag())
                    }

                    commitAllowingStateLoss()

                    if (isExecutePending) {
                        try {
                            executePendingTransactions()
                        } catch (exception: NullPointerException) {
                            // ignore exception
                        }
                    }
                }
            }
        }
}

const val ACTION_ADD = 0
const val ACTION_REPLACE = 1
const val ACTION_ADD_AND_HIDE_OTHERS = 2