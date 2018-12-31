package com.computer.inu.myworkinggings.fragment

import android.opengl.Visibility
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
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
import com.computer.inu.myworkinggings.Moohyeon.Adapter.SearchingUserRecyclerViewAdapter
import com.computer.inu.myworkinggings.Moohyeon.Data.SearchingUserData
import org.jetbrains.anko.support.v4.toast


class DirectoryFragment : Fragment(){
    lateinit var directoryboardRecyclerViewAdapter : DirectoryBoardRecyclerViewAdapter
     lateinit var searchingUserRecyclerViewAdapter: SearchingUserRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.fragment_directory, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setRecyclerView()
        setUserRecyclerView()
        rv_item_SearchingUserlist.visibility=View.GONE
       et_directory_searching.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(et_directory_searching.text.toString().equals("검색")){
                    iv_directory_searching.visibility=View.GONE
                    tv_directory_searching_text.visibility=View.GONE
                    rv_item_SearchingUserlist.visibility=View.VISIBLE
                }

            }
        })

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

  private fun setUserRecyclerView(){
      var UserDataList : ArrayList<SearchingUserData> = ArrayList()
      UserDataList.add(SearchingUserData("이충엽","깅스","PM","기획","협업"))
      UserDataList.add(SearchingUserData("김무현","깅스","안드로이드","개발","능력"))
      UserDataList.add(SearchingUserData("문현진","깅스","안드로이드","개발","기술"))
      UserDataList.add(SearchingUserData("송제민","깅스","안드로이드","개발","힘"))
      UserDataList.add(SearchingUserData("양승희","깅스","안드로이드","개발","지식"))
      searchingUserRecyclerViewAdapter = SearchingUserRecyclerViewAdapter(activity!!,UserDataList)
      rv_item_SearchingUserlist.adapter = searchingUserRecyclerViewAdapter
      rv_item_SearchingUserlist.layoutManager=GridLayoutManager(activity,2)
  }
    private fun setRecyclerView(){
        //임시데이터
        var dataList: ArrayList<DirectoryBoardData> = ArrayList()
        dataList.add(DirectoryBoardData("김무현","2018/12/29/03:21","안녕하세요~ 저는 김무현입니다!"))
        dataList.add(DirectoryBoardData("김바보","2018/12/27/05:21","안녕하세요~ 저는 김바보입니다!"))
        dataList.add(DirectoryBoardData("김천재","2018/12/25/03:25","안녕하세요~ 저는 김천재입니다!"))
        dataList.add(DirectoryBoardData("김김김","2018/12/26/05:26","안녕하세요~ 저는 김김김입니다!"))

        directoryboardRecyclerViewAdapter =DirectoryBoardRecyclerViewAdapter(activity!!, dataList)
        rv_directory_user_list.adapter = directoryboardRecyclerViewAdapter
        rv_directory_user_list.layoutManager = LinearLayoutManager(activity)
    }
}