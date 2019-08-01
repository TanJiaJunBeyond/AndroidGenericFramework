package com.tanjiajun.dadarecycle

import android.content.Intent
import android.os.Bundle
import com.tanjiajun.dadarecycle.user.ui.user.activity.RegisterAndLoginActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, RegisterAndLoginActivity::class.java))
    }

}
