package com.computer.inu.myworkinggings.Moohyeon.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {

    lateinit var networkService : NetworkService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        networkService = ApplicationController.instance.networkService
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
