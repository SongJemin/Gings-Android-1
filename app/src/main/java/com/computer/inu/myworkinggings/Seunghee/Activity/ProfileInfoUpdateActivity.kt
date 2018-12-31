package com.sopt.gings

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_profile_info_update.*

class ProfileInfoUpdateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_info_update)

        bt_profile_info_update_complete.setOnClickListener {
            val intent: Intent = Intent(this, ProfileSettingMenuActivity::class.java)
            startActivity(intent)
        }

    }
}
