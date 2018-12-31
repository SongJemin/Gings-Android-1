package com.computer.inu.myworkinggings.Seunghee.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Adapter.BoardRecyclerViewAdapter
import com.computer.inu.myworkinggings.Seunghee.Activity.CategoryMenuActivity
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

        setRecyclerView()

        iv_btn_home_board_category.setOnClickListener {
            startActivity<CategoryMenuActivity>()
        }
    }
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setRecyclerView()
//    }

    private fun setRecyclerView(){

        //val anyDataList : ArrayList<Any> = arrayListOf("dtdt")//자바의 object. 최상위 데이터타입

        //임시데이터
        var dataList: ArrayList<BoardData> = ArrayList()
        //dataList.add(BoardData("협업", "창업에 관심 있으신 분!!!", "#cowalk", "오후 6:53","사진 없는뎁..", "이 편지는 행운의 편지입니다", true, "사진없눈뎁", "이충엽", "깅스" , "쵝오이 피엠", 1, 1))
        //dataList.add(BoardData("협업", "창업에 관심 있으신 분!!!", "#cowalk", "오후 6:53","사진 없는뎁..", "이 편지는 행운의 편지입니다", "사진없눈뎁", "이충엽", "깅스" , "쵝오이 피엠", 1, 1))
        dataList.add(BoardData("협업", "창업에 관심 있으신 분!!!", "#cowalk", "오후 6:53", "이 편지는 행운의 편지입니다", "이충엽", "깅스", "쵝오이 피엠", 1, 1))

        dataList.add(BoardData("창업", "창업에 관심 있으신 분!!!", "#cowalk", "오후 6:53", "이 편지는 행운의 편지입니다", "이충엽", "깅스", "쵝오이 피엠", 1, 1))

        dataList.add(BoardData("창", "창업에 관심 있으신 분!!!", "#cowalk", "오후 6:53", "이 편지는 행운의 편지입니다", "이충엽", "깅스", "쵝오이 피엠", 1, 1))

        dataList.add(BoardData("협업", "창업에 관심 있으신 분!!!", "#cowalk", "오후 6:53", "이 편지는 행운의 편지입니다", "이충엽", "깅스", "쵝오이 피엠", 1, 1))

        /*dataList.add(BoardData("영감", "영감이 떠오른드아!!!", "#배고푸다", "오후 4:43"))
        dataList.add(BoardData("협업", "깅스쵝오!!!", "하배고파", "오후 3:03"))
        dataList.add(BoardData("질문", "깅스 빡쎈가여?", "1주차", "오후 2:33"))
        dataList.add(BoardData("질문", "깅스 빡쎄냐구여!!","2주차" , "오후 1:13"))
        dataList.add(BoardData("영감", "창업가에게 도움이 되는 페이지 입니다.!", "3주차", "오전 5:53"))*/

        //BoardRecyclerViewAdapter = BoardRecyclerViewAdapter(this, dataList)
        boardRecyclerViewAdapter = BoardRecyclerViewAdapter(activity!!, dataList)
        rv_item_board_list.adapter = boardRecyclerViewAdapter
        rv_item_board_list.layoutManager = LinearLayoutManager(activity)

    }
}