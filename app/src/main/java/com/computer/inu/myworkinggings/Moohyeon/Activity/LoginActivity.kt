package com.computer.inu.myworkinggings.Moohyeon.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.computer.inu.myworkinggings.R
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
           startActivity<DetailBoardActivity>()
        }
        tv_login_login_button.setOnClickListener {
            startActivity<BottomNaviActivity>()
        }
    }

}
