package com.tanjiajun.androidgenericframework.utils

import android.widget.Toast
import com.tanjiajun.androidgenericframework.AndroidGenericFrameworkApplication

/**
 * Created by TanJiaJun on 2020-02-13.
 */
fun toastShort(text: String) =
        Toast.makeText(AndroidGenericFrameworkApplication.instance, text, Toast.LENGTH_SHORT).show()

fun toastLong(text: String) =
        Toast.makeText(AndroidGenericFrameworkApplication.instance, text, Toast.LENGTH_LONG).show()
