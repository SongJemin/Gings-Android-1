package com.computer.inu.myworkinggings.Seunghee.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
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

        //키보드를 띄운다.
        //inputMethodManager.showSoftInput(mTitle, 0)

        //if(inputMethodManager.showSoftInput(mLinearLayout, 0))
        //gl_upboard_album_view.visibility=View.GONE



        /*각각의 카테고리 선택 리스너*/
        //카테고리 선택
        val categoryBtnList : Array<RelativeLayout> = arrayOf(
                rl_btn_up_board_category_question,
                rl_btn_up_board_category_inspriation,
                rl_btn_up_board_category_collaboration
        )
        val categoryListText : Array<TextView> = arrayOf(tv_up_board_question,tv_up_board_inspiration,tv_up_board_collaboration)
        //카테고리 선택시, 선택 창 닫힘 + 헤당 카테고리 글자 띄우기
        for(i in categoryBtnList.indices)
        {
            categoryBtnList[i].setOnClickListener{
                rl_btn_up_board_category_selected.visibility=View.GONE
                categoryListText[i].visibility=View.VISIBLE

                ll_up_board_category_list.visibility=View.GONE
            }
        }

        for(i in categoryListText.indices)
        {
            categoryListText[i].setOnClickListener{
                categoryListText[i].visibility=View.GONE
                rl_btn_up_board_category_selected.visibility=View.VISIBLE

                ll_up_board_category_list.visibility=View.VISIBLE
            }
        }


        /*카테고리 선택 창 열고 닫는 리스너*/
        //열기
        rl_btn_up_board_category_select.setOnClickListener{

            //more버튼 대신 less버튼으로
            rl_btn_up_board_category_select.visibility=View.GONE
            rl_btn_up_board_category_selected.visibility=View.VISIBLE

            //카테고리 리스트 선택 창
            ll_up_board_category_list.visibility=View.VISIBLE

        }
        //닫기
        rl_btn_up_board_category_selected.setOnClickListener{

            rl_btn_up_board_category_selected.visibility=View.GONE
            rl_btn_up_board_category_select.visibility=View.VISIBLE

            //카테고리 리스트 선택 창
            ll_up_board_category_list.visibility=View.GONE
        }



        /*사진선택*/
        //이미지 버튼 클릭시
        iv_upboard_input_image.setOnClickListener {

            //이미지 창 나타내기
            ll_upboard_album_view.visibility=View.VISIBLE

            //키보드 창 내리기
            inputMethodManager.hideSoftInputFromWindow(mLinearLayout.windowToken, 0)
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
        //하단 이미지선택
        setRecyclerView()

    }

    private fun setRecyclerView(){

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
}
