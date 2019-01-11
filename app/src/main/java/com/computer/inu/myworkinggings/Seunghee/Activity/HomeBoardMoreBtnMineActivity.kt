package com.computer.inu.myworkinggings.Seunghee.Activity

import com.computer.inu.myworkinggings.Seunghee.Fragment.HomeBoardFragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.computer.inu.myworkinggings.Jemin.Activity.MainActivity
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Fragment.HomeBoardFragment.Companion.boardRecyclerViewAdapter
import com.computer.inu.myworkinggings.Seunghee.Post.DeleteBoardResponse
import com.computer.inu.myworkinggings.Seunghee.db.SharedPreferenceController
import kotlinx.android.synthetic.main.activity_home_board_more_btn_mine.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeBoardMoreBtnMineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        var position : Int = 0

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
                    SharedPreferenceController.getAuthorization(this),
                    modifyBoardID)

            deleteBoardResponse.enqueue(object : Callback<DeleteBoardResponse> {
                override fun onFailure(call: Call<DeleteBoardResponse>, t: Throwable) {
                    Log.e("sign up fail", t.toString())
                    toast("삭제 실패")
                }

                //통신 성공 시 수행되는 메소드
                override fun onResponse(call: Call<DeleteBoardResponse>, response: Response<DeleteBoardResponse>) {
                    if (response.isSuccessful) {
                        toast("삭제 성공")
                        //startActivity<MainActivity>()
                        arrayOf(HomeBoardFragment.boardRecyclerViewAdapter)

                        position = intent.getIntExtra("Position", 0)

                        boardRecyclerViewAdapter.notifyItemRemoved(position)
                        boardRecyclerViewAdapter.notifyItemRangeRemoved(position,1)
                        finish()
                    }
                }
            })

        }


    }

}
