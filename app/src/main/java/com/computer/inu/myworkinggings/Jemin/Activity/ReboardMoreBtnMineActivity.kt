package com.computer.inu.myworkinggings.Jemin.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Post.DeleteReboardResponse
import com.computer.inu.myworkinggings.Seunghee.db.SharedPreferenceController
import kotlinx.android.synthetic.main.activity_reboard_more_btn_mine.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReboardMoreBtnMineActivity : AppCompatActivity() {


    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reboard_more_btn_mine)

        var reboardId: Int = 0
        reboardId = intent.getIntExtra("reboardId", 0)

        reboard_more_modify_btn.setOnClickListener {
            Log.v("asdf", "받은 리보드 번호 = " + reboardId)

            val intent = Intent()
            intent.putExtra("reboardId", reboardId)
            setResult(30, intent)
            finish()
        }

        reboard_more_delete_btn.setOnClickListener {




        }
    }
}

