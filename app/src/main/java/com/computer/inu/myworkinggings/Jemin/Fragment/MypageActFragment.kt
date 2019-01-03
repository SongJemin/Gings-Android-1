package com.computer.inu.myworkinggings.Jemin.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.computer.inu.myworkinggings.Jemin.Adapter.GuestActAdapter
import com.computer.inu.myworkinggings.Jemin.Adapter.GuestBoardAdapter
import com.computer.inu.myworkinggings.Jemin.Data.GuestActItem
import com.computer.inu.myworkinggings.Jemin.Data.GuestBoardItem
import com.computer.inu.myworkinggings.R
import kotlinx.android.synthetic.main.fragment_mypage_act.view.*
import kotlinx.android.synthetic.main.fragmet_my_page_introduce.view.*

class MypageActFragment : Fragment(){

    var guestActItem =  ArrayList<GuestActItem>()
    lateinit var guestActAdapter : GuestActAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View = inflater.inflate(R.layout.fragment_mypage_act,container,false)

        guestActItem.add(GuestActItem("창업하고 싶은데 어떻게 준비하면 좋을까요?", "#사업 #창업","3시간전","01:ㅁㄴㅇㅎㅁㄴㅇㄹㅎㅇㅁㄶㄴㅁㅇㄹㄴㅁㅎㅁㄴㅇㄻㅇㄶㅎㅇ","이충엽","깅스/PM"))
        guestActItem.add(GuestActItem("놀고싶은데 어떻게 해야해요?", "#놀기 #술술","3시간전","ㅁㄴㅇㅎㅁㄴㅇㄹㅎㅇㅁㄶㄴㅁㅇㄹㄴㅁㅎㅁㄴㅇㄻㅇㄶㅎㅇ","이충엽","깅스/PM"))
        guestActItem.add(GuestActItem("끝이 안나요", "#안녕 #집가기","3시간전","ㅁㄴㅇㅎㅁㄴㅇㄹㅎㅇㅁㄶㄴㅁㅇㄹㄴㅁㅎㅁㄴㅇㄻㅇㄶㅎㅇ","이충엽","깅스/PM"))
        guestActItem.add(GuestActItem("배고파요", "#밥 #밥밥","3시간전","ㅁㄴㅇㅎㅁㄴㅇㄹㅎㅇㅁㄶㄴㅁㅇㄹㄴㅁㅎㅁㄴㅇㄻㅇㄶㅎㅇ","ㅎㅇㅁㄴㅇㅎㅁㄴㅇㄹㅎㅇ","깅스/PM"))
        guestActItem.add(GuestActItem("ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ", "#ㅠㅠ #ㅠㅠ","3시간전","01:16","이충엽","깅스/PM"))
        guestActAdapter = GuestActAdapter(guestActItem)
        v.mypage_act_recyclerview.layoutManager = LinearLayoutManager(v.context)
        v.mypage_act_recyclerview.adapter = guestActAdapter
        v.mypage_act_recyclerview.setNestedScrollingEnabled(false)

        return v
    }
}