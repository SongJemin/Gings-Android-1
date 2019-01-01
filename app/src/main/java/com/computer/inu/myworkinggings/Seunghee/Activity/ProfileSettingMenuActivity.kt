package com.computer.inu.myworkinggings.Seunghee.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.computer.inu.myworkinggings.R
import kotlinx.android.synthetic.main.activity_profile_setting_menu.*

class ProfileSettingMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_setting_menu)

        //종료버튼
        iv_btn_profile_setting_close.setOnClickListener {
            finish()
        }
    }
}
