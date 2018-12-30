package com.computer.inu.myworkinggings.Jemin.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.computer.inu.myworkinggings.Jemin.Adapter.GuestBoardAdapter
import com.computer.inu.myworkinggings.Jemin.Data.GuestBoardItem
import com.computer.inu.myworkinggings.R
import kotlinx.android.synthetic.main.fragmet_my_page_introduce.view.*

class MypageIntroFragment : Fragment(){
    var guestBoardItem =  ArrayList<GuestBoardItem>()
    lateinit var guestBoardAdapter : GuestBoardAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View = inflater.inflate(R.layout.fragmet_my_page_introduce,container,false)

        guestBoardItem.add(GuestBoardItem("이충엽", "컴퓨터/iOS Developer","2018/12/30","01:16","ㅎㅇㅎㅇ"))
        guestBoardItem.add(GuestBoardItem("이충엽2", "디자이너","2018/11/30","01:16","ㅁㄴㅇㅎㅁㄴㅇㄹㅎㅇㅁㄶㄴㅁㅇㄹㄴㅁㅎㅁㄴㅇㄻㅇㄶㅎㅇ"))
        guestBoardItem.add(GuestBoardItem("이충엽3", "메인PM","2018/10/30","01:16","ㅎㅇㅎㅁㄴㅇㄻㄴㅇㅎㅁㄴㅇㄻㅇㄶㅁㄴㅇㄹㅇ"))
        guestBoardItem.add(GuestBoardItem("이충엽4", "서버 개발자","2018/12/30","01:16","ㅎㅇㅁㄴㅇㅎㅁㄴㅇㄹㅎㅇ"))
        guestBoardItem.add(GuestBoardItem("이충엽5", "안드로이드 개발자","2018/12/30","01:16","ㅁㄴㅇㅎㅁㄴㅇㄹㄴㅁㅎㅎㅇㅎㅇ"))
        guestBoardAdapter = GuestBoardAdapter(guestBoardItem)
        v.mypage_guestboard_recyclerview.layoutManager = LinearLayoutManager(v.context)
        v.mypage_guestboard_recyclerview.adapter = guestBoardAdapter
        return v
    }
}