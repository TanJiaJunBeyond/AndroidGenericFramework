package com.tanjiajun.androidgenericframework.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.tanjiajun.androidgenericframework.R

/**
 * Created by TanJiaJun on 2019-08-25.
 */
@BindingAdapter("android:src")
fun ImageView.setImageSource(@DrawableRes resId: Int) =
        setImageResource(resId)

@BindingAdapter("onNavigationIconClick")
fun Toolbar.setOnNavigationIconClickListener(listener: View.OnClickListener) =
        setNavigationOnClickListener(listener)

@BindingAdapter(value = ["url", "placeholder", "error"], requireAll = false)
fun ImageView.loadImage(url: String?, placeholder: Drawable?, error: Drawable?) =
        Glide
                .with(context)
                .load(url)
                .placeholder(placeholder ?: context.getDrawable(R.mipmap.ic_launcher))
                .error(error ?: context.getDrawable(R.mipmap.ic_launcher))
                .transition(DrawableTransitionOptions.withCrossFade(DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()))
                .into(this)