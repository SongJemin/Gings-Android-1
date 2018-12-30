package com.computer.inu.myworkinggings.fragment

import android.opengl.Visibility
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.computer.inu.myworkinggings.Moohyeon.Adapter.DirectoryBoardRecyclerViewAdapter
import com.computer.inu.myworkinggings.Moohyeon.Data.DirectoryBoardData
import com.computer.inu.myworkinggings.R
import kotlinx.android.synthetic.main.fragment_directory.*

import kotlinx.android.synthetic.main.fragment_home_board.*
import org.jetbrains.anko.support.v4.ctx
import android.widget.Toast
import android.view.KeyEvent
import android.widget.TextView.OnEditorActionListener



class DirectoryFragment : Fragment(){
    lateinit var directoryboardRecyclerViewAdapter : DirectoryBoardRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.fragment_directory, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setRecyclerView()
        iv_directory_finish.visibility=View.GONE
        et_directory_searching.inputType=InputType.TYPE_CLASS_TEXT
      et_directory_searching.imeOptions=EditorInfo.IME_ACTION_SEARCH
 et_directory_searching.setOnClickListener {
     rv_directory_user_list.visibility = View.GONE
     tv_directory_welcome.visibility=View.GONE
     iv_directory_finish.visibility=View.VISIBLE
     iv_directory_searching.visibility=View.VISIBLE
     tv_directory_searching_text.visibility=View.VISIBLE
 }
        iv_directory_finish.setOnClickListener {
            rv_directory_user_list.visibility = View.VISIBLE
            tv_directory_welcome.visibility=View.VISIBLE

            iv_directory_finish.visibility=View.GONE
            iv_directory_searching.visibility=View.GONE
            tv_directory_searching_text.visibility=View.GONE
        }

}


    private fun setRecyclerView(){

        //val anyDataList : ArrayList<Any> = arrayListOf("dtdt")//자바의 object. 최상위 데이터타입

        //임시데이터
        var dataList: ArrayList<DirectoryBoardData> = ArrayList()
        //dataList.add(DirectoryData("협업", "창업에 관심 있으신 분!!!", "#cowalk", "오후 6:53","사진 없는뎁..", "이 편지는 행운의 편지입니다", true, "사진없눈뎁", "이충엽", "깅스" , "쵝오이 피엠", 1, 1))
        //dataList.add(DirectoryData("협업", "창업에 관심 있으신 분!!!", "#cowalk", "오후 6:53","사진 없는뎁..", "이 편지는 행운의 편지입니다", "사진없눈뎁", "이충엽", "깅스" , "쵝오이 피엠", 1, 1))
        dataList.add(DirectoryBoardData("김무현","2018/12/29/03:21","안녕하세요~ 저는 김무현입니다!"))
        dataList.add(DirectoryBoardData("김바보","2018/12/27/05:21","안녕하세요~ 저는 김바보입니다!"))
        dataList.add(DirectoryBoardData("김천재","2018/12/25/03:25","안녕하세요~ 저는 김천재입니다!"))
        dataList.add(DirectoryBoardData("김김김","2018/12/26/05:26","안녕하세요~ 저는 김김김입니다!"))
        /*dataList.add(DirectoryData("영감", "영감이 떠오른드아!!!", "#배고푸다", "오후 4:43"))
        dataList.add(DirectoryData("협업", "깅스쵝오!!!", "하배고파", "오후 3:03"))
        dataList.add(DirectoryData("질문", "깅스 빡쎈가여?", "1주차", "오후 2:33"))
        dataList.add(DirectoryData("질문", "깅스 빡쎄냐구여!!","2주차" , "오후 1:13"))
        dataList.add(DirectoryData("영감", "창업가에게 도움이 되는 페이지 입니다.!", "3주차", "오전 5:53"))*/
                directoryboardRecyclerViewAdapter =DirectoryBoardRecyclerViewAdapter(activity!!, dataList)
        rv_directory_user_list.adapter = directoryboardRecyclerViewAdapter
        rv_directory_user_list.layoutManager = LinearLayoutManager(activity)

    }
}