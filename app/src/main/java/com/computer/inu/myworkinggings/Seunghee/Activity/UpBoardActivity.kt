package com.computer.inu.myworkinggings.Seunghee.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Adapter.AlbumRecyclerViewAdapter
import com.sopt.gings.data.AlbumData
import kotlinx.android.synthetic.main.activity_up_board.*


class UpBoardActivity : AppCompatActivity() {

    lateinit var AlbumRecyclerViewAdapter: AlbumRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_up_board)

        val mLinearLayout = findViewById(R.id.ll_upboard_album_view) as LinearLayout//기능상 키보드가 사라짐과 연관있는 view를 사용하면 된다.

        //키보드 제어
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        /*
        iv_upboard_input_image.setOnClickListener{
            addFragment(AlbumFragment())
        }*/

        //이미지 버튼 클릭시
        iv_upboard_input_image.setOnClickListener {

            //이미지 창 나타내기
            ll_upboard_album_view.visibility=View.VISIBLE

            //키보드 창 내리기
            inputMethodManager.hideSoftInputFromWindow(mLinearLayout.windowToken, 0)
        }


        //
        tv_up_board_category.setOnClickListener{
            ll_up_board_category_list.visibility=View.VISIBLE
        }

        //et뷰 클릭시. 이미지 창 감추기
        et_up_board_title.setOnClickListener {
            ll_upboard_album_view.visibility=View.GONE
        }

        et_up_board_tags.setOnClickListener {
            ll_upboard_album_view.visibility=View.GONE
        }

        et_up_board_modify.setOnClickListener {
            ll_upboard_album_view.visibility=View.GONE
        }


        //키보드를 띄운다.
        //inputMethodManager.showSoftInput(mTitle, 0)

        //if(inputMethodManager.showSoftInput(mLinearLayout, 0))
        //gl_upboard_album_view.visibility=View.GONE


        //제목 글자 수 체크


        //키워드 개수 체크

        setRecyclerView()

    }

    private fun setRecyclerView(){

        //val anyDataList : ArrayList<Any> = arrayListOf("dtdt")//자바의 object. 최상위 데이터타입

        //임시데이터set
        var dataList: ArrayList<AlbumData> = ArrayList()
        dataList.add(AlbumData(true))
        dataList.add(AlbumData(true))
        dataList.add(AlbumData(true))
        dataList.add(AlbumData(true))
        dataList.add(AlbumData(true))
        dataList.add(AlbumData(true))
        dataList.add(AlbumData(true))
        dataList.add(AlbumData(true))
        dataList.add(AlbumData(true))
        dataList.add(AlbumData(true))
        dataList.add(AlbumData(true))
        dataList.add(AlbumData(true))


        AlbumRecyclerViewAdapter = AlbumRecyclerViewAdapter(this, dataList)
        rv_upboard_album_list.adapter = AlbumRecyclerViewAdapter
        rv_upboard_album_list.layoutManager = GridLayoutManager(this,3)

    }
/*
    private fun addFragment(fragment : Fragment){
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_upboard_act_fragment_block, fragment)
        transaction.commit()
    }*/
}
