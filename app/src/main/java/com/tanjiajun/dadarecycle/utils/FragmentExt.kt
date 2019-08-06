package com.tanjiajun.dadarecycle.utils

import androidx.fragment.app.Fragment
import com.tanjiajun.dadarecycle.DaDaRecycleApplication
import com.tanjiajun.dadarecycle.ui.ViewModelFactory

/**
 * Created by TanJiaJun on 2019-08-07.
 */
fun Fragment.getViewModelFactory(): ViewModelFactory =
    ViewModelFactory((requireContext().applicationContext as DaDaRecycleApplication).userRepository)