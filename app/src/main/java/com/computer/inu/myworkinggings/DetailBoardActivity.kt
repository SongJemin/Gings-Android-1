package com.computer.inu.myworkinggings

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.computer.inu.myworkinggings.adapter.BoardRecyclerViewAdapter
import com.computer.inu.myworkinggings.adapter.DetailBoardRecyclerViewAdapter
import com.computer.inu.myworkinggings.data.BoardData
import com.computer.inu.myworkinggings.data.ReBoardData
import kotlinx.android.synthetic.main.activity_detail_board.*
import kotlinx.android.synthetic.main.fragment_home_board.*
import org.jetbrains.anko.activityManager
import org.jetbrains.anko.ctx

class DetailBoardActivity : AppCompatActivity() {
    lateinit var  detailBoardRecyclerViewAdapter : DetailBoardRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_board)
        setRecyclerView()
    }

    private fun setRecyclerView(){
            //val anyDataList : ArrayList<Any> = arrayListOf("dtdt")//자바의 object. 최상위 데이터타입
            //임시데이터
            var dataList: ArrayList<ReBoardData> = ArrayList()
           dataList.add(ReBoardData("김무현","3시간전","안녕하세요!"))
            dataList.add(ReBoardData("김무현1","1시간전","ㅎㅇ"))
            dataList.add(ReBoardData("김무현2","3시간전","ㄱㄱ!"))
            dataList.add(ReBoardData("김무현3","8시간전","ㄱㄱ!"))
            dataList.add(ReBoardData("김무현4","6시간전","안녕하세요!"))
            dataList.add(ReBoardData("김무현5","3시간전","ㄱㄱ세요!"))
            dataList.add(ReBoardData("김무현6","4시간전","ㄱㄱㄱ!"))
            dataList.add(ReBoardData("김무현7","5시간전","안녕하세요!"))
            dataList.add(ReBoardData("김무현8","6시간전","ㅎㅎㅎㅎㅎㅎ!"))

            //BoardRecyclerViewAdapter = BoardRecyclerViewAdapter(this, dataList)
           detailBoardRecyclerViewAdapter = DetailBoardRecyclerViewAdapter(this , dataList)
            rv_item_detailboard_list.adapter = detailBoardRecyclerViewAdapter
            rv_item_detailboard_list.layoutManager =LinearLayoutManager(this)
            rv_item_detailboard_list.canScrollVertically(0)

        }

    }