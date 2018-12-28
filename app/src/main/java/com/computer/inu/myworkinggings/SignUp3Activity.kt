package com.computer.inu.myworkinggings

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_up3.*
import org.jetbrains.anko.startActivity

class SignUp3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up3)
        tv_sign_up3_sign_up_complete.setOnClickListener {
            startActivity<LoginActivity>()
        }
    }
}
