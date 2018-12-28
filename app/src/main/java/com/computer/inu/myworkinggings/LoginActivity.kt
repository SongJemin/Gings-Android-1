package com.computer.inu.myworkinggings

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        tv_login_join_us.setOnClickListener {
      startActivity<SignUp1Activity>()
        }
        tv_login_about_gings.setOnClickListener {
            startActivity<TestActivity>()
        }
        tv_login_login_button.setOnClickListener {
            startActivity<BottomNaviActivity>()
        }
    }

}
