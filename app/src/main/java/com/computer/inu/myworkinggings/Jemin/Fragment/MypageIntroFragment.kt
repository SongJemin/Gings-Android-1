package com.computer.inu.myworkinggings.Jemin.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.computer.inu.myworkinggings.Jemin.Adapter.GuestBoardAdapter
import com.computer.inu.myworkinggings.Jemin.Data.GuestBoardItem

import com.computer.inu.myworkinggings.Jemin.Get.Response.GetOtherIntroResponse
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R

import com.computer.inu.myworkinggings.Moohyeon.get.GetGuestBoardResponse
import com.computer.inu.myworkinggings.Moohyeon.get.GetMypageIntroduceResponse
import kotlinx.android.synthetic.main.fragment_my_page.*
import kotlinx.android.synthetic.main.fragmet_my_page_introduce.*
import kotlinx.android.synthetic.main.fragmet_my_page_introduce.view.*
import org.jetbrains.anko.support.v4.ctx

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

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    lateinit var requestManager : RequestManager
  /*  var GuestBoardDataList = ArrayList<GuestBoardData>()*/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View = inflater.inflate(R.layout.fragmet_my_page_introduce,container,false)
        val extra = arguments



        field = extra!!.getString("field")
        Log.v("asdf", "받는 필드 = " + field)
        status = extra!!.getString("status")
        coworkingEnabled = extra!!.getInt("coworkingEnabled")
        if(coworkingEnabled == 1){
            v.mypage_intro_collab_tv.text = "가능"
        }
        else{
            v.mypage_intro_collab_tv.text = "불가능"
        }
        v.mypage_intro_field_tv.text = field
        v.mypage_intro_status_tv.text = status
        /*getOtherIntro()*/ //타인
      getMyIntro() //자신의 소개페이지




        requestManager = Glide.with(this)

        getGuestBoardPost()

        guestBoardAdapter = GuestBoardAdapter(context!!,guestBoardItem)
        v.mypage_guestboard_recyclerview.layoutManager = LinearLayoutManager(v.context)
        v.mypage_guestboard_recyclerview.adapter = guestBoardAdapter
        v.mypage_guestboard_recyclerview.setNestedScrollingEnabled(false)
        return v
    }


    fun getOtherIntro() {
        var getOtherIntroResponse = networkService.getOtherPageIntro("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg", 1) // 네트워크 서비스의 getContent 함수를 받아옴
        getOtherIntroResponse.enqueue(object : Callback<GetOtherIntroResponse> {
            override fun onResponse(call: Call<GetOtherIntroResponse>?, response: Response<GetOtherIntroResponse>?) {
                Log.v("TAG", "타인 소개 페이지 서버 통신 연결")
                if (response!!.isSuccessful) {
                    Log.v("TAG", "타인 소개 페이지 서버 통신 연결 성공")
                    mypage_board_content_tv.text = response.body()!!.data.content
                    mypage_board_datetime_tv.text = response.body()!!.data.time!!.substring(0, 16).replace("T", "   ")

                }
            }

            override fun onFailure(call: Call<GetOtherIntroResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " + t.toString())
            }
        })
    }
    fun getMyIntro() {
        var getMypageIntroduceResponse = networkService.getMypageIntroduceResponse("application/json","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg") // 네트워크 서비스의 getContent 함수를 받아옴
       getMypageIntroduceResponse.enqueue(object : Callback<GetMypageIntroduceResponse> {
            override fun onResponse(call: Call<GetMypageIntroduceResponse>?, response: Response<GetMypageIntroduceResponse>?) {
                Log.v("TAG", "나의 소개 페이지 서버 통신 연결")
                if (response!!.isSuccessful) {
                    Log.v("MyTAG", "나의 소개 페이지 서버 통신 연결 성공")
                    var temp = response.body()!!.data.imgs
                    mypage_board_content_tv.text = response.body()!!.data.content
                    mypage_board_datetime_tv.text = response.body()!!.data.time!!.substring(0, 16).replace("T", "   ")

                    // 사진 넣어야함
                }
            }

            override fun onFailure(call: Call<GetMypageIntroduceResponse>?, t: Throwable?) {
                Log.v("MyTAG", "통신 실패 = " + t.toString())
            }
        })
    }
    fun getGuestBoardPost(){
        var getGuestResponse: Call<GetGuestBoardResponse>  = networkService.getGuestBoardResponse("application/json","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg")
        getGuestResponse.enqueue(object : Callback<GetGuestBoardResponse> {
            override fun onResponse(call: Call<GetGuestBoardResponse>?, response: Response<GetGuestBoardResponse>?) {
                Log.v("TAG", "보드 서버 통신 연결")
                if (response!!.isSuccessful) {
                    val temp : ArrayList<GuestBoardItem> = response.body()!!.data

                    if(temp.size>0){
                        val position = guestBoardAdapter.itemCount
                        guestBoardAdapter.guestBoardItems.addAll(temp)
                        guestBoardAdapter.notifyItemInserted(position)
                    }



                }
            }

            override fun onFailure(call: Call<GetGuestBoardResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })

    }

}