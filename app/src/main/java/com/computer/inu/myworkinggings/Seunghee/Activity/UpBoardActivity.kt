package com.computer.inu.myworkinggings.Seunghee.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import android.widget.TextView
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Adapter.AlbumRecyclerViewAdapter
import gun0912.tedbottompicker.TedBottomPicker
import kotlinx.android.synthetic.main.activity_up_board.*
import java.util.ArrayList


class UpBoardActivity : AppCompatActivity() {

    lateinit var AlbumRecyclerViewAdapter: AlbumRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_up_board)

        //키보드 제어
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        //키보드를 띄운다.
        //inputMethodManager.showSoftInput(mTitle, 0)

        //if(inputMethodManager.showSoftInput(mLinearLayout, 0))
        //gl_upboard_album_view.visibility=View.GONE


        /*카테고리 선택&재선택 함수*/
        categorySelectOnClickListener()

        /*사진선택*/
        //이미지 버튼 클릭시
        iv_upboard_input_image.setOnClickListener {
            //키보드 창 내리기
            //inputMethodManager.hideSoftInputFromWindow(mLinearLayout.windowToken, 0)
            val tedBottomPicker = TedBottomPicker.Builder(this@UpBoardActivity)
                    .setOnMultiImageSelectedListener {
                        uriList: ArrayList<Uri>? ->
                        for(i in 0 .. uriList!!.size-1){
                            uriList!!.add(uriList.get(i))
                            Log.v("TAG","이미지 = " + uriList.get(i))
                        }
                    }
                    .setSelectMaxCount(4)
                    .showCameraTile(false)
                    .setPeekHeight(800)
                    .showTitle(false)
                    .setEmptySelectionText("선택된게 없습니다! 이미지를 선택해 주세요!")
                    .create()

            tedBottomPicker.show(supportFragmentManager)
        }
    }

    private fun categorySelectOnClickListener(){

        /*각각의 카테고리 선택*/
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

        //카테고리 선택 완료 후, 재 선택
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
    }

}
