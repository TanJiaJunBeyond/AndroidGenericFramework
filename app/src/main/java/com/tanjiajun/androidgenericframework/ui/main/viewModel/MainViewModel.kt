package com.tanjiajun.androidgenericframework.ui.main.viewModel

import android.view.View
import com.tanjiajun.androidgenericframework.ui.BaseViewModel

/**
 * Created by TanJiaJun on 2019-08-24.
 */
class MainViewModel : BaseViewModel() {

    interface Handlers {

        fun onPersonalCenterClick(view: View)

    }

}