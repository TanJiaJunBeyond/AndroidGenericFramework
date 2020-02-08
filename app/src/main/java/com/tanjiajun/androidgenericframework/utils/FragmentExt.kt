package com.tanjiajun.androidgenericframework.utils

import androidx.fragment.app.Fragment
import com.tanjiajun.androidgenericframework.AndroidGenericFrameworkApplication
import com.tanjiajun.androidgenericframework.ui.BaseActivity
import com.tanjiajun.androidgenericframework.ui.ViewModelFactory

/**
 * Created by TanJiaJun on 2019-08-07.
 */
inline fun <reified T : BaseActivity<*>> Fragment.startActivity() =
        startActivity(android.content.Intent(context, T::class.java))

fun Fragment.getViewModelFactory(): ViewModelFactory =
        with(requireContext().applicationContext as AndroidGenericFrameworkApplication) {
            ViewModelFactory(userRepository, repositoryOfGitHubRepository)
        }