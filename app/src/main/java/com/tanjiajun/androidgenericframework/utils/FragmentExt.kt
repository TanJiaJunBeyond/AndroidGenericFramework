package com.tanjiajun.androidgenericframework.utils

import androidx.fragment.app.Fragment
import com.tanjiajun.androidgenericframework.ui.BaseActivity

/**
 * Created by TanJiaJun on 2019-08-07.
 */
inline fun <reified T : BaseActivity<*, *>> Fragment.startActivity() =
        startActivity(android.content.Intent(context, T::class.java))