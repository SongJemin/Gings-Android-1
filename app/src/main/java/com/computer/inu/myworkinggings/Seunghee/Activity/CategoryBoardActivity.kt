package com.computer.inu.myworkinggings.Seunghee.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.computer.inu.myworkinggings.Jemin.Data.BoardItem
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Adapter.BoardRecyclerViewAdapter
import com.computer.inu.myworkinggings.Seunghee.GET.GetCategoryBoardResponse
import com.computer.inu.myworkinggings.data.BoardData
import kotlinx.android.synthetic.main.activity_category_board.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CategoryBoardActivity : AppCompatActivity() {

    //adapter
    lateinit var boardRecyclerViewAdapter: BoardRecyclerViewAdapter
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    lateinit var requestManager: RequestManager

    //임시데이터
    var dataList: ArrayList<BoardData> = ArrayList()

    var BoardData = ArrayList<com.computer.inu.myworkinggings.Jemin.Get.Response.GetData.BoardData>()
    var BoardItemList = ArrayList<BoardItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_board)
        requestManager = Glide.with(this)

        var category_name: String = intent.getStringExtra("category_name")
        tv_category_board_category_name.setText(category_name)

        val directoryListText: Array<String> = arrayOf("질문", "영감", "협업", "샘플1", "샘플2", "샘플3", "샘플4", "샘플5", "샘플6")
        val directoryListCode: Array<String> = arrayOf("QUESTION", "INSPIRATION", "COWORKING")

        for (i in directoryListCode.indices) {
            if (directoryListText[i] == category_name)
                category_name = directoryListCode[i]
        }

        getCategoryBoardResponse(category_name)

    }

    private fun getCategoryBoardResponse(category_code: String) {

        val getCategoryboardResponse = networkService.getCategoryBoardResponse("application/json",
                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg",
                category_code
        )

        getCategoryboardResponse.enqueue(object : Callback<GetCategoryBoardResponse> {


            override fun onFailure(call: Call<GetCategoryBoardResponse>, t: Throwable) {
                Log.e(" fail", t.toString())
            }

            override fun onResponse(call: Call<GetCategoryBoardResponse>, response: Response<GetCategoryBoardResponse>) {
                if (response.isSuccessful) {

                    BoardData = response.body()!!.data

                    for (i in 0..BoardData.size - 1) {
                        Log.v("asdf", "키워드 크기 = " + BoardData[i].keywords.size)
                        BoardItemList.add(BoardItem(BoardData[i].boardId,
                                BoardData[i].writerId, BoardData[i].writer,
                                BoardData[i].writerImage, BoardData[i].field, BoardData[i].company,
                                BoardData[i].title, BoardData[i].content,
                                BoardData[i].share, BoardData[i].time,
                                BoardData[i].category, BoardData[i].images,
                                BoardData[i].keywords, BoardData[i].numOfReply,
                                BoardData[i].recommender)
                        )

                    }
                    Log.v("asdf", "응답 바디 = " + response.body().toString())

                    boardRecyclerViewAdapter = BoardRecyclerViewAdapter(ctx, BoardItemList, requestManager)
                    rv_item_category_board_list.adapter = boardRecyclerViewAdapter
                    rv_item_category_board_list.layoutManager = LinearLayoutManager(ctx)

                } else {
                    ctx.toast("pleas")
                }
            }
        })
    }
}


