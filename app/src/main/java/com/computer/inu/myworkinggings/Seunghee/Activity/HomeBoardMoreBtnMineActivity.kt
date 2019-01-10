package com.computer.inu.myworkinggings.Seunghee.Activity


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Post.DeleteBoardResponse
import kotlinx.android.synthetic.main.activity_home_board_more_btn_mine.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeBoardMoreBtnMineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val networkService: NetworkService by lazy {
            ApplicationController.instance.networkService
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_board_more_btn_mine)


        rl_btn_home_board_more_btn_mine_modify.setOnClickListener {

            val modifyBoardID: Int = intent.getIntExtra("BoardId", 0).toInt()
            startActivity<UpBoardActivity>("ModifyBoardID" to modifyBoardID)
        }

        rl_btn_home_board_more_btn_mine_delete.setOnClickListener {

            val modifyBoardID: Int = intent.getIntExtra("BoardId", 0).toInt()
            val deleteBoardResponse: Call<DeleteBoardResponse> = networkService.deleteBoardResponse("application/json",
                    "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg",
                    modifyBoardID)

            deleteBoardResponse.enqueue(object : Callback<DeleteBoardResponse> {
                override fun onFailure(call: Call<DeleteBoardResponse>, t: Throwable) {
                    Log.e("sign up fail", t.toString())
                }

                //통신 성공 시 수행되는 메소드
                override fun onResponse(call: Call<DeleteBoardResponse>, response: Response<DeleteBoardResponse>) {
                    if (response.isSuccessful) {
                        finish()
                    }
                }
            })

        }


    }

}
