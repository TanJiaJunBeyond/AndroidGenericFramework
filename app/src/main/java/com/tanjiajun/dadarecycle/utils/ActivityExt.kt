package com.tanjiajun.dadarecycle.utils

import android.app.Activity
import com.tanjiajun.dadarecycle.DaDaRecycleApplication
import com.tanjiajun.dadarecycle.ui.BaseActivity
import com.tanjiajun.dadarecycle.ui.ViewModelFactory

/**
 * Created by TanJiaJun on 2019-08-12.
 */
inline fun <reified T : BaseActivity> Activity.startActivity() =
        startActivity(android.content.Intent(this, T::class.java))

fun Activity.getViewModelFactory(): ViewModelFactory =
        ViewModelFactory((applicationContext as DaDaRecycleApplication).userRepository)