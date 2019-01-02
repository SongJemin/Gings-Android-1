package com.computer.inu.myworkinggings.Seunghee.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.computer.inu.myworkinggings.Jemin.Data.BoardItem
import com.computer.inu.myworkinggings.Jemin.Get.Response.BoardData
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetBoardResponse
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetEmailRedundancyResponse
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.Seunghee.Adapter.BoardRecyclerViewAdapter
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Activity.CategoryMenuActivity
import com.computer.inu.myworkinggings.Seunghee.Activity.UpBoardActivity
import kotlinx.android.synthetic.main.fragment_home_board.*
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeBoardFragment : Fragment(){
    lateinit var boardRecyclerViewAdapter : BoardRecyclerViewAdapter
    lateinit var networkService : NetworkService
    var BoardData = ArrayList<BoardData>()
    var BoardItemList = ArrayList<BoardItem>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.fragment_home_board, container, false)
        networkService = ApplicationController.instance.networkService
        Log.v("TAG", "보드 서버 통신 연결준비")
        getBoard()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //리사이클러뷰
        setRecyclerView()

        //게시글 작성 버튼
        tv_home_board_write_board.setOnClickListener{
            startActivity<UpBoardActivity>()
        }

        //카테고리 메뉴바 열기
        iv_btn_home_board_category.setOnClickListener {
            startActivity<CategoryMenuActivity>()
        }


    }

    private fun setRecyclerView(){




    }

    fun getBoard() {
        var getBoardResponse = networkService.getBoard(0,10) // 네트워크 서비스의 getContent 함수를 받아옴
        Log.v("TAG", "보드 서버 통신 연결준비2")
        getBoardResponse.enqueue(object : Callback<GetBoardResponse> {
            override fun onResponse(call: Call<GetBoardResponse>?, response: Response<GetBoardResponse>?) {
                Log.v("TAG", "보드 서버 통신 연결")
                if (response!!.isSuccessful) {
                    BoardData = response.body()!!.data

                    for(i in 0..BoardData.size-1){
                        BoardItemList.add(BoardItem(BoardData[i].boardId, BoardData[i].writerId, BoardData[i].title, BoardData[i].content, BoardData[i].share, BoardData[i].time, BoardData[i].category, BoardData[i].images, BoardData[i].keywords, BoardData[i].replys, BoardData[i].recommender ))
                    }
                    Log.v("ㅁㄴㅇㄹ","보드 Get 통신 성공")
                    Log.v("asdf","응답 바디 = " + response.body().toString())

                    boardRecyclerViewAdapter = BoardRecyclerViewAdapter(activity!!, BoardItemList)
                    rv_item_board_list.adapter = boardRecyclerViewAdapter
                    rv_item_board_list.layoutManager = LinearLayoutManager(activity)
                }
            }

            override fun onFailure(call: Call<GetBoardResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })
    }
}