package com.tanjiajun.androidgenericframework.ui

import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.tanjiajun.androidgenericframework.R
import com.tanjiajun.androidgenericframework.utils.otherwise
import com.tanjiajun.androidgenericframework.utils.toastShort
import com.tanjiajun.androidgenericframework.utils.yes
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Created by TanJiaJun on 2019-07-28.
 */
abstract class BaseActivity<T : ViewDataBinding, VM : BaseViewModel> : DaggerAppCompatActivity() {

    lateinit var binding: T

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var manager: FragmentManager
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)
        manager = supportFragmentManager
    }

    @get:LayoutRes
    abstract val layoutRes: Int

    abstract val viewModel: VM

    open val containId: Int = 0

    protected fun registerToastEvent() =
            viewModel.uiLiveEvent.showToastEvent.observe(this, Observer { toastShort(it) })

    protected fun registerLoadingProgressBarEvent() =
            with(viewModel.uiLiveEvent) {
                showLoadingProgressBarEvent.observe(this@BaseActivity, Observer {
                    findViewById<FrameLayout>(android.R.id.content).addView(
                            ProgressBar(this@BaseActivity)
                                    .apply {
                                        layoutParams = FrameLayout.LayoutParams(
                                                FrameLayout.LayoutParams.WRAP_CONTENT,
                                                FrameLayout.LayoutParams.WRAP_CONTENT
                                        ).also { it.gravity = Gravity.CENTER }
                                    }
                                    .also { progressBar = it }
                    )
                })
                dismissLoadingProgressBarEvent.observe(this@BaseActivity, Observer {
                    progressBar?.let { findViewById<FrameLayout>(android.R.id.content).removeView(it) }
                })
            }

    protected fun registerSnackbarEvent() =
            viewModel.uiLiveEvent.showSnackbarEvent.observe(this, Observer {
                Snackbar
                        .make(window.decorView, it, Snackbar.LENGTH_SHORT)
                        .setActionTextColor(ContextCompat.getColor(this@BaseActivity, R.color.white))
                        .show()
            })

    fun getCurrentFragment(): BaseFragment<*, *> =
            manager.findFragmentById(containId) as BaseFragment<*, *>

    fun getFragmentList(): List<Fragment?> =
            mutableListOf<Fragment?>().apply {
                val count = manager.backStackEntryCount
                for (i in 0 until count) {
                    add(manager.findFragmentByTag(manager.getBackStackEntryAt(i).name))
                }
            }

    fun getPreviousFragment(): BaseFragment<*, *>? =
            manager.backStackEntryCount
                    .takeIf { it >= 2 }
                    ?.let {
                        manager.findFragmentByTag(manager.getBackStackEntryAt(it.minus(2)).name) as BaseFragment<*, *>?
                    }

    fun popBackStackImmediate(): Boolean =
            manager
                    .takeIf { it.backStackEntryCount > 0 }
                    ?.popBackStackImmediate()
                    ?: false

    fun popBackStackImmediate(name: String, flags: Int): Boolean =
            manager
                    .takeIf { it.backStackEntryCount > 0 }
                    ?.popBackStackImmediate(name, flags)
                    ?: false

    fun addFragment(fragment: BaseFragment<*, *>) =
            handleFragmentTransaction(
                    fragment = fragment,
                    action = ACTION_ADD,
                    isAddToBackStack = true,
                    isExecutePending = true
            )

    fun addFragmentAndHideOthers(fragment: BaseFragment<*, *>) =
            handleFragmentTransaction(
                    fragment = fragment,
                    action = ACTION_ADD_AND_HIDE_OTHERS,
                    isAddToBackStack = true,
                    isExecutePending = true
            )

    fun addFragmentAndHideOthersNotExecutePending(fragment: BaseFragment<*, *>) =
            handleFragmentTransaction(
                    fragment = fragment,
                    action = ACTION_ADD_AND_HIDE_OTHERS,
                    isAddToBackStack = true,
                    isExecutePending = false
            )

    fun replaceFragment(fragment: BaseFragment<*, *>, addToBackStack: Boolean = true) =
            handleFragmentTransaction(
                    fragment = fragment,
                    action = ACTION_REPLACE,
                    isAddToBackStack = addToBackStack,
                    isExecutePending = true
            )

    protected fun addFragmentNotAddToBackStack(fragment: BaseFragment<*, *>) =
            handleFragmentTransaction(
                    fragment = fragment,
                    action = ACTION_ADD,
                    isAddToBackStack = false,
                    isExecutePending = false
            )

    fun addFragmentNotExecutePending(fragment: BaseFragment<*, *>) =
            handleFragmentTransaction(
                    fragment = fragment,
                    action = ACTION_ADD,
                    isAddToBackStack = true,
                    isExecutePending = false
            )

    private fun handleFragmentTransaction(fragment: BaseFragment<*, *>,
                                          action: Int,
                                          isAddToBackStack: Boolean,
                                          isExecutePending: Boolean) =
            with(manager) {
                beginTransaction().let {
                    if (fragment.enableAnimation) it.setCustomAnimations(
                            fragment.enterAnimation,
                            fragment.exitAnimation,
                            fragment.popEnterAnimation,
                            fragment.popExitAnimation
                    )

                    handleFragment(action, containId, fragment, manager, it, isAddToBackStack)
                    it.commitAllowingStateLoss()

                    if (isExecutePending) {
                        try {
                            executePendingTransactions()
                        } catch (exception: NullPointerException) {
                            // ignore exception
                        }
                    }
                }
            }

    private fun handleFragment(action: Int,
                               containId: Int,
                               fragment: BaseFragment<*, *>,
                               manager: FragmentManager,
                               transaction: FragmentTransaction,
                               isAddToBackStack: Boolean) =
            with(manager) {
                when (action) {
                    ACTION_ADD -> transaction.add(containId, fragment, fragment.transactionTag)
                    ACTION_REPLACE -> transaction.replace(containId, fragment, fragment.transactionTag)
                    ACTION_ADD_AND_HIDE_OTHERS -> {
                        fragments
                                .filter { it.isVisible }
                                .forEach { transaction.hide(it) }
                        transaction.add(containId, fragment, fragment.transactionTag)
                    }
                    else -> Unit
                }
                if (isAddToBackStack) transaction.addToBackStack(fragment.transactionTag)
            }

    override fun onBackPressed() =
            with(manager) {
                (backStackEntryCount > getFragmentCountToFinish())
                        .yes {
                            (findFragmentById(containId) as BaseFragment<*, *>).let {
                                popBackStackImmediate()
                                it.onHandleGoBack()
                            }
                        }
                        .otherwise { onFinishActivity() }
            }

    private fun getFragmentCountToFinish() = 1

    private fun onFinishActivity() {
        finish()
        overridePendingTransition(0, 0)
    }

    private companion object {
        const val ACTION_ADD = 0
        const val ACTION_REPLACE = 1
        const val ACTION_ADD_AND_HIDE_OTHERS = 2
    }

}