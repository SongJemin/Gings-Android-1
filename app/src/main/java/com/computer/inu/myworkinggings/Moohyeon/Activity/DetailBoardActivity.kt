package com.computer.inu.myworkinggings.Moohyeon.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.computer.inu.myworkinggings.Moohyeon.Adapter.DetailBoardRecyclerViewAdapter
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.GET.DetailedBoardData
import com.computer.inu.myworkinggings.Seunghee.GET.GetDetailedBoardResponse
import com.computer.inu.myworkinggings.Seunghee.GET.ReplyData
import kotlinx.android.synthetic.main.activity_detail_board.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailBoardActivity : AppCompatActivity() {
    lateinit var detailBoardRecyclerViewAdapter: DetailBoardRecyclerViewAdapter
    val networkService: com.computer.inu.myworkinggings.Network.NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_board)

        getDetailedBoardResponse()

    }


    private fun getDetailedBoardResponse() {


        val getDetailedBoardResponse = networkService.getDetailedBoardResponse("application/json", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg", intent.getIntExtra("BoardId", 0).toInt())
        //toast(intent.getIntExtra("BoardId",0))

        getDetailedBoardResponse.enqueue(object : Callback<GetDetailedBoardResponse> {
            override fun onFailure(call: Call<GetDetailedBoardResponse>, t: Throwable) {
                Log.e("detailed_board fail", t.toString())
            }

            override fun onResponse(call: Call<GetDetailedBoardResponse>, response: Response<GetDetailedBoardResponse>) {
                if (response.isSuccessful) {

                    //toast(intent.getIntExtra("BoardId",0))
                    Log.v("ggg", "board list success")
                    //Toast.makeText(applicationContext,"성공",Toast.LENGTH_SHORT).show()

                    //보드연결
                    val temp: DetailedBoardData = response.body()!!.data
                    bindBoardData(temp)

                    //리보드연결
                    val reboardtemp : ArrayList<ReplyData?> = response.body()!!.data.replys
                    bindReBoardData(reboardtemp)
                }
            }

        })
    }

    private fun bindBoardData(temp: DetailedBoardData) {

        /*타이틀*/
        tv_detail_board_time.text = temp.time
        tv_detail_board_category.text = temp.category
        tv_detail_board_title.text = temp.title
        //태그
        val TagList: Array<TextView> = arrayOf(tv_detail_board_tag1,
                tv_detail_board_tag2,
                tv_detail_board_tag3,
                tv_detail_board_tag4,
                tv_detail_board_tag5
        )
        for (i in TagList.indices) {
            if (i < temp.keywords.size)
                TagList[i].text = "#" + temp.keywords[i]
            else
                TagList[i].text = null
        }

        /*contents*/
        //텍스트
        tv_detail_board_contents_text.text = temp.content
        //이미지
        lateinit var requestManager : RequestManager
        requestManager = Glide.with(this)
        for(i in 0..temp.images.size-1 )
            requestManager.load(temp.images[0]).into(iv_detail_board_contents_image)

        /* profile */
        //개인정보
        tv_item_board_profile_name.text = temp.writer
        tv_item_board_profile_role.text = temp.field
        tv_item_board_profile_team.text = temp.company
        //추천&댓글
        tv_item_board_like_cnt.text=temp.recommender.toString()
        tv_item_board_comment_cnt.text = temp.numOfReply.toString()

        //tv_item_board_profile_role.text = temp.
        //tv_item_board_profile_team.text = temp.

    }


    private fun bindReBoardData(temp : ArrayList<ReplyData?> ){

        detailBoardRecyclerViewAdapter = DetailBoardRecyclerViewAdapter(this, temp)
        rv_item_detailboard_list.adapter = detailBoardRecyclerViewAdapter
        rv_item_detailboard_list.layoutManager = LinearLayoutManager(this)
        rv_item_detailboard_list.canScrollVertically(0)

    }
}