package com.computer.inu.myworkinggings.Seunghee.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Adapter.BoardRecyclerViewAdapter
import com.computer.inu.myworkinggings.data.BoardData
import kotlinx.android.synthetic.main.activity_category_board.*


class CategoryBoardActivity : AppCompatActivity() {

    lateinit var categoryBoardRecyclerViewAdapter : BoardRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_board)

        setRecyclerView()

    }


    private fun setRecyclerView(){

        //임시데이터
        var dataList: ArrayList<BoardData> = ArrayList()
        //dataList.add(BoardData("협업", "창업에 관심 있으신 분!!!", "#cowalk", "오후 6:53","사진 없는뎁..", "이 편지는 행운의 편지입니다", "사진없눈뎁", "이충엽", "깅스" , "쵝오이 피엠", 1, 1))
        dataList.add(BoardData("협업", "창업에 관심 있으신 분!!!", "#cowalk", "오후 6:53", "이 편지는 행운의 편지입니다", "이충엽", "깅스", "쵝오이 피엠", 1, 1))
        dataList.add(BoardData("창업", "창업에 관심 있으신 분!!!", "#cowalk", "오후 6:53", "이 편지는 행운의 편지입니다", "이충엽", "깅스", "쵝오이 피엠", 1, 1))
        dataList.add(BoardData("협업", "창업에 관심 있으신 분!!!", "#cowalk", "오후 6:53", "이 편지는 행운의 편지입니다", "이충엽", "깅스", "쵝오이 피엠", 1, 1))
        dataList.add(BoardData("협업", "창업에 관심 있으신 분!!!", "#cowalk", "오후 6:53", "이 편지는 행운의 편지입니다", "이충엽", "깅스", "쵝오이 피엠", 1, 1))

        categoryBoardRecyclerViewAdapter = BoardRecyclerViewAdapter(this, dataList)
        rv_item_category_board_list.adapter = categoryBoardRecyclerViewAdapter
        rv_item_category_board_list.layoutManager = LinearLayoutManager(this)

    }
}


