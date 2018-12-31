package com.computer.inu.myworkinggings.Jemin.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.computer.inu.myworkinggings.R
import kotlinx.android.synthetic.main.activity_passwd_modify.*

class PasswdModifyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passwd_modify)

        // 테스트 연결
        passwd_modify_confirm_btn.setOnClickListener {
            val intent = Intent(applicationContext, PasswordConfirmActivity::class.java)
            startActivity(intent)
        }
    }
}
