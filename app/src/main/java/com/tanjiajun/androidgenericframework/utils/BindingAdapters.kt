package com.tanjiajun.androidgenericframework.utils

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter

/**
 * Created by TanJiaJun on 2019-08-25.
 */
@BindingAdapter("app:onNavigationIconClick")
fun Toolbar.setOnNavigationIconClickListener(listener: View.OnClickListener) {
    setNavigationOnClickListener(listener)
}