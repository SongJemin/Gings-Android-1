package com.computer.inu.myworkinggings.Seunghee.Activity


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Post.DeleteReboardResponse
import kotlinx.android.synthetic.main.activity_home_board_more_btn_mine.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReBoardMoreBtnMineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val networkService: NetworkService by lazy {
            ApplicationController.instance.networkService
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_board_more_btn_mine)


        rl_btn_home_board_more_btn_mine_modify.setOnClickListener {

        }

        rl_btn_home_board_more_btn_mine_delete.setOnClickListener {

            val deleteReBoardID: Int = intent.getIntExtra("ReBoardId", 0).toInt()
            val deleteReboardResponse: Call<DeleteReboardResponse> = networkService.deleteReboardResponse("application/json",
                    "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg",
                    deleteReBoardID)

            deleteReboardResponse.enqueue(object : Callback<DeleteReboardResponse>{
                override fun onFailure(call: Call<DeleteReboardResponse>, t: Throwable) {
                    Log.e("sign up fail", t.toString())
                }

                override fun onResponse(call: Call<DeleteReboardResponse>, response: Response<DeleteReboardResponse>) {
                    if (response.isSuccessful) {
                        ctx.toast("성고오옹")
                        finish()
                    }
                }
            })
        }
    }

}
