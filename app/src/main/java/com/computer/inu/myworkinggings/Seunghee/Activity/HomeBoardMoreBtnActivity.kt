package com.computer.inu.myworkinggings.Seunghee.Activity


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Post.PostBlockBoardIDResponse
import kotlinx.android.synthetic.main.activity_home_board_more_btn.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeBoardMoreBtnActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_board_more_btn)

        //boardRecyclerViewAdapter.dataList.clear()

        rl_btn_home_board_more_block_boardID.setOnClickListener {
            getBoardShareResponse()
            //통신
        }
    }

    //보드 가리기 통신
    private fun getBoardShareResponse() {
        val getBoardshareResponse = networkService.postBlockBoardIDResponse("application/json",
                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg",
                intent.getIntExtra("BoardId", 0)
        )

        getBoardshareResponse.enqueue(object : Callback<PostBlockBoardIDResponse> {
            override fun onFailure(call: Call<PostBlockBoardIDResponse>, t: Throwable) {
                Log.e("보드공유 통신 fail", t.toString())
            }

            override fun onResponse(call: Call<PostBlockBoardIDResponse>, response: Response<PostBlockBoardIDResponse>) {
                if (response.isSuccessful) {
                    Log.e("보드공유 통신성공", "  통신 성공")
                    toast("보드 가리기 성공")
                }
            }
        })
    }

}

