package com.computer.inu.myworkinggings.Jemin.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.computer.inu.myworkinggings.R
import kotlinx.android.synthetic.main.activity_reboard_more_btn_mine.*

class ReboardMoreBtnMineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reboard_more_btn_mine)

        reboard_more_modify_btn.setOnClickListener {
            var reboardId : Int = 0
            reboardId = intent.getIntExtra("reboardId", 0)
            Log.v("asdf", "받은 리보드 번호 = " + reboardId)

            val intent = Intent()
            intent.putExtra("reboardId", reboardId)
            setResult(30, intent)
            finish()
        }
    }


}
