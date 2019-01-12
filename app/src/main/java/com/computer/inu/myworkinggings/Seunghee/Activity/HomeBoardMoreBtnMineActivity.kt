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
    var position : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {


        val networkService: NetworkService by lazy {
            ApplicationController.instance.networkService
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_board_more_btn_mine)


        val modifyBoardID: Int = intent.getIntExtra("BoardId", 0)


        rl_btn_home_board_more_btn_mine_modify.setOnClickListener {

            startActivity<UpBoardActivity>("ModifyBoardID" to modifyBoardID)

        }

        rl_btn_home_board_more_btn_mine_delete.setOnClickListener {

            //삭제 통신
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

                        //if(intent.getBooleanExtra("isDetailed", false))
                            //finish()
                        //startActivity<MainActivity>()
                        //arrayOf(HomeBoardFragment.boardRecyclerViewAdapter)
                        Log.e("보드공유 통신성공", "  통신 성공")

                        position = intent.getIntExtra("Position", 0)

                        boardRecyclerViewAdapter.notifyItemRemoved(position)
                        boardRecyclerViewAdapter.notifyItemRangeRemoved(position,1)


                        finish()
                    }
                }
            })

        }


    }

    @Override
    override fun onBackPressed()
    {
        super.onBackPressed()

        finish()
        overridePendingTransition(R.anim.stay, android.R.anim.fade_out)
    }

}
