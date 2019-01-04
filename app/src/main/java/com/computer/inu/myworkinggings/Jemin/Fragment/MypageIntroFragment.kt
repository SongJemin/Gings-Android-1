package com.computer.inu.myworkinggings.Jemin.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.computer.inu.myworkinggings.Jemin.Adapter.GuestBoardAdapter
import com.computer.inu.myworkinggings.Jemin.Data.GuestBoardItem
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetOtherInformResponse
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetOtherIntroResponse
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import kotlinx.android.synthetic.main.fragment_my_page.*
import kotlinx.android.synthetic.main.fragment_mypage_act.view.*
import kotlinx.android.synthetic.main.fragmet_my_page_introduce.*
import kotlinx.android.synthetic.main.fragmet_my_page_introduce.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageIntroFragment : Fragment(){
    var guestBoardItem =  ArrayList<GuestBoardItem>()
    lateinit var guestBoardAdapter : GuestBoardAdapter
    var field : String = ""
    var status : String = ""
    var coworkingEnabled : Int = 0
    var checkFlag : Int = 0
    lateinit var networkService : NetworkService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View = inflater.inflate(R.layout.fragmet_my_page_introduce,container,false)
        val extra = arguments



        field = extra!!.getString("field")
        Log.v("asdf", "받는 필드 = " + field)
        status = extra!!.getString("status")
        coworkingEnabled = extra!!.getInt("coworkingEnabled")
        if(coworkingEnabled == 1){
            v.mypage_intro_on_tv.text = "ON"
        }
        else{
            v.mypage_intro_on_tv.text = "OFF"
        }
        v.mypage_intro_field_tv.text = field
        v.mypage_intro_status_tv.text = status
        getOtherIntro()




        guestBoardItem.add(GuestBoardItem("이충엽", "컴퓨터/iOS Developer","2018/12/30","01:16","ㅎㅇㅎㅇ"))
        guestBoardItem.add(GuestBoardItem("이충엽2", "디자이너","2018/11/30","01:16","ㅁㄴㅇㅎㅁㄴㅇㄹㅎㅇㅁㄶㄴㅁㅇㄹㄴㅁㅎㅁㄴㅇㄻㅇㄶㅎㅇ"))
        guestBoardItem.add(GuestBoardItem("이충엽3", "메인PM","2018/10/30","01:16","ㅎㅇㅎㅁㄴㅇㄻㄴㅇㅎㅁㄴㅇㄻㅇㄶㅁㄴㅇㄹㅇ"))
        guestBoardItem.add(GuestBoardItem("이충엽4", "서버 개발자","2018/12/30","01:16","ㅎㅇㅁㄴㅇㅎㅁㄴㅇㄹㅎㅇ"))
        guestBoardItem.add(GuestBoardItem("이충엽5", "안드로이드 개발자","2018/12/30","01:16","ㅁㄴㅇㅎㅁㄴㅇㄹㄴㅁㅎㅎㅇㅎㅇ"))
        guestBoardAdapter = GuestBoardAdapter(guestBoardItem)
        v.mypage_guestboard_recyclerview.layoutManager = LinearLayoutManager(v.context)
        v.mypage_guestboard_recyclerview.adapter = guestBoardAdapter
        v.mypage_guestboard_recyclerview.setNestedScrollingEnabled(false)
        return v
    }

    fun getOtherIntro() {
        networkService = ApplicationController.instance.networkService
        var getOtherIntroResponse = networkService.getOtherPageIntro("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg",1) // 네트워크 서비스의 getContent 함수를 받아옴
        getOtherIntroResponse.enqueue(object : Callback<GetOtherIntroResponse> {
            override fun onResponse(call: Call<GetOtherIntroResponse>?, response: Response<GetOtherIntroResponse>?) {
                Log.v("TAG", "타인 소개 페이지 서버 통신 연결")
                if (response!!.isSuccessful) {
                    Log.v("TAG", "타인 소개 페이지 서버 통신 연결 성공")
                    mypage_board_content_tv.text = response.body()!!.data.content
                    mypage_board_datetime_tv.text = response.body()!!.data.time!!.substring(0,16).replace("T","   ")

                }
            }

            override fun onFailure(call: Call<GetOtherIntroResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })
    }
}