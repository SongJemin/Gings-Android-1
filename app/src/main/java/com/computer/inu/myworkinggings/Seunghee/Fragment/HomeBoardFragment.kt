package com.computer.inu.myworkinggings.Seunghee.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.computer.inu.myworkinggings.Seunghee.Adapter.BoardRecyclerViewAdapter
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Activity.CategoryMenuActivity
import com.computer.inu.myworkinggings.UpBoardActivity
import com.computer.inu.myworkinggings.data.BoardData
import kotlinx.android.synthetic.main.fragment_home_board.*
import org.jetbrains.anko.support.v4.startActivity


class HomeBoardFragment : Fragment(){
    lateinit var boardRecyclerViewAdapter : BoardRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.fragment_home_board, container, false)

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

        //임시데이터
        var dataList: ArrayList<BoardData> = ArrayList()
        //dataList.add(BoardData("협업", "창업에 관심 있으신 분!!!", "#cowalk", "오후 6:53","사진 없는뎁..", "이 편지는 행운의 편지입니다", true, "사진없눈뎁", "이충엽", "깅스" , "쵝오이 피엠", 1, 1))
        //dataList.add(BoardData("협업", "창업에 관심 있으신 분!!!", "#cowalk", "오후 6:53","사진 없는뎁..", "이 편지는 행운의 편지입니다", "사진없눈뎁", "이충엽", "깅스" , "쵝오이 피엠", 1, 1))
        dataList.add(BoardData("협업", "창업에 관심 있으신 분!!!", "#cowalk", "오후 6:53", "이 편지는 행운의 편지입니다", "이충엽", "깅스", "쵝오이 피엠", 1, 1))

        dataList.add(BoardData("창업", "창업에 관심 있으신 분!!!", "#cowalk", "오후 6:53", "이 편지는 행운의 편지입니다", "이충엽", "깅스", "쵝오이 피엠", 1, 1))


        dataList.add(BoardData("창", "창업에 관심 있으신 분!!!", "#cowalk", "오후 6:53", "이 편지는 행운의 편지입니다", "이충엽", "깅스", "쵝오이 피엠", 1, 1))
        dataList.add(BoardData("협업", "창업에 관심 있으신 분!!!", "#cowalk", "오후 6:53", "이 편지는 행운의 편지입니다", "이충엽", "깅스", "쵝오이 피엠", 1, 1))


        boardRecyclerViewAdapter = BoardRecyclerViewAdapter(activity!!, dataList)
        rv_item_board_list.adapter = boardRecyclerViewAdapter
        rv_item_board_list.layoutManager = LinearLayoutManager(activity)

    }
}