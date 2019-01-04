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
import com.computer.inu.myworkinggings.Jemin.Data.BoardItem
import com.computer.inu.myworkinggings.Jemin.Data.GuestBoardItem
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetBoardResponse
import com.computer.inu.myworkinggings.Moohyeon.Data.GuestBoardData
import com.computer.inu.myworkinggings.Moohyeon.get.GetGuestBoardResponse
import com.computer.inu.myworkinggings.Moohyeon.post.PostSignUpResponse
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Adapter.BoardRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_home_board.*
import kotlinx.android.synthetic.main.fragment_mypage_act.view.*
import kotlinx.android.synthetic.main.fragmet_my_page_introduce.*
import kotlinx.android.synthetic.main.fragmet_my_page_introduce.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageIntroFragment : Fragment(){

    lateinit var guestBoardAdapter : GuestBoardAdapter
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    lateinit var requestManager : RequestManager
    var guestBoardItem =  ArrayList<GuestBoardItem>()
  /*  var GuestBoardDataList = ArrayList<GuestBoardData>()*/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View = inflater.inflate(R.layout.fragmet_my_page_introduce,container,false)

        requestManager = Glide.with(this)

        getGuestBoardPost()

        guestBoardAdapter = GuestBoardAdapter(context!!,guestBoardItem)
        v.rv_mypage_guestboard_recyclerview.layoutManager = LinearLayoutManager(v.context)
        v.rv_mypage_guestboard_recyclerview.adapter = guestBoardAdapter
        v.rv_mypage_guestboard_recyclerview.setNestedScrollingEnabled(false)
        return v
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