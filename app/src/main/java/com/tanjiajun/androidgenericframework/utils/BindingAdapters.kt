package com.tanjiajun.androidgenericframework.utils

import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.tanjiajun.androidgenericframework.R

/**
 * Created by TanJiaJun on 2019-08-25.
 */
@BindingAdapter("onNavigationIconClick")
fun Toolbar.setOnNavigationIconClickListener(listener: View.OnClickListener) =
        setNavigationOnClickListener(listener)

@BindingAdapter(value = ["url", "placeholder", "error"], requireAll = false)
fun ImageView.loadImage(url: String?, placeholder: Int?, error: Int?) =
        Glide
                .with(context)
                .load(url)
                .placeholder(placeholder ?: R.mipmap.ic_launcher)
                .error(error ?: R.mipmap.ic_launcher)
                .into(this)