package com.computer.inu.myworkinggings.Jemin.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.computer.inu.myworkinggings.R
import kotlinx.android.synthetic.main.activity_password_confirm.*

class PasswordConfirmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_confirm)

        // 테스트 연결
        password_confirm_confirm_btn.setOnClickListener {
            val intent = Intent(applicationContext, UnsubscribeActivity::class.java)
            startActivity(intent)
        }
    }
}
