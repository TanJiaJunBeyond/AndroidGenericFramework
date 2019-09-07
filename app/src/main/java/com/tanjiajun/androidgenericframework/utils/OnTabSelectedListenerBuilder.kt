package com.tanjiajun.androidgenericframework.utils

import com.google.android.material.tabs.TabLayout

/**
 * Created by TanJiaJun on 2019-09-07.
 */
private typealias OnTabCallback = (tab: TabLayout.Tab?) -> Unit

fun registerOnTabSelectedListener(function: OnTabSelectedListenerBuilder.() -> Unit) =
        OnTabSelectedListenerBuilder().also(function)

class OnTabSelectedListenerBuilder : TabLayout.OnTabSelectedListener {

    private var onTabReselectedCallback: OnTabCallback? = null
    private var onTabUnselectedCallback: OnTabCallback? = null
    private var onTabSelectedCallback: OnTabCallback? = null

    override fun onTabReselected(tab: TabLayout.Tab?) =
            onTabReselectedCallback?.invoke(tab) ?: Unit

    override fun onTabUnselected(tab: TabLayout.Tab?) =
            onTabUnselectedCallback?.invoke(tab) ?: Unit

    override fun onTabSelected(tab: TabLayout.Tab?) =
            onTabSelectedCallback?.invoke(tab) ?: Unit

    fun onTabReselected(callback: OnTabCallback) {
        onTabReselectedCallback = callback
    }

    fun onTabUnselected(callback: OnTabCallback) {
        onTabUnselectedCallback = callback
    }

    fun onTabSelected(callback: OnTabCallback) {
        onTabSelectedCallback = callback
    }

}