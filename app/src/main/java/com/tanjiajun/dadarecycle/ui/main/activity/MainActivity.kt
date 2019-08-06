package com.tanjiajun.dadarecycle.ui.main.activity

import android.content.Intent
import android.os.Bundle
import com.tanjiajun.dadarecycle.R
import com.tanjiajun.dadarecycle.ui.BaseActivity
import com.tanjiajun.dadarecycle.ui.user.activity.RegisterAndLoginActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, RegisterAndLoginActivity::class.java))
    }

}
