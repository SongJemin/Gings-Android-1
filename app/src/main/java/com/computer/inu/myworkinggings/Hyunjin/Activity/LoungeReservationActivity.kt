package com.computer.inu.myworkinggings.Hyunjin.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.computer.inu.myworkinggings.Hyunjin.Get.GetEventSearch
import com.computer.inu.myworkinggings.Hyunjin.Post.PostJoinEvent
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import kotlinx.android.synthetic.main.activity_lounge_reservation.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoungeReservationActivity : AppCompatActivity() {
    lateinit var networkService : NetworkService

    override fun onCreate(savedInstanceState: Bundle?) {
        networkService = ApplicationController.instance.networkService
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lounge_reservation)
        getEventSearch()

        btn_lounge_event_detail_join.setOnClickListener{
            btn_lounge_event_detail_join.visibility=View.GONE
            btn_lounge_event_detail_waiting.visibility=View.VISIBLE
            postJoinEvent()
        }
        btn_lounge_reservation_back.setOnClickListener{
            finish()
        }
    }

    //이벤트 조회 통신
    fun getEventSearch(){
        var getEventSearchResponse = networkService.getEventSearch("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkxOTYxMzN9.OrlfMuYaMa2SqrXGcHlDRmttGOC1z7DiROKD4dsz2Ds",intent.getIntExtra("clubId",0),intent.getIntExtra("eventId",0)) // 네트워크 서비스의 getContent 함수를 받아옴
        getEventSearchResponse.enqueue(object : Callback<GetEventSearch> {
            override fun onResponse(call: Call<GetEventSearch>, response: Response<GetEventSearch>) {
                Log.v("TAG", "이벤트 조회 성공 GET 통신 성공")
                if (response.isSuccessful) {
                    Log.v("TAG", "이벤트 조회 성공")
                    Log.v("TAG", "status = " + response.body()!!.status)
                    Log.v("TAG", "message = " + response.body()!!.message)

                    tv_lounge_eventsearch_title.text=response.body()!!.data.title
                    tv_lounge_event_title.text=response.body()!!.data.title
                    tv_lounge_event_detail_date.text=response.body()!!.data.date
                    tv_lounge_event_detail_time.text=response.body()!!.data.time
                    tv_lounge_detail_place.text=response.body()!!.data.place
                    tv_lounge_detail_price.text=response.body()!!.data.price
                    Glide.with(ctx).load(response.body()!!.data.eventImg).centerCrop().into(iv_lounge_event_search_eventImg)
                    Glide.with(ctx).load(response.body()!!.data.detailImg).centerCrop().into(iv_rl_lounge_detail_program)

                    if(response.body()!!.data.eventStatus=="참여하기"){
                        btn_lounge_event_detail_join.visibility=View.VISIBLE
                    }
                    else if(response.body()!!.data.eventStatus=="참여완료"){
                        btn_lounge_event_detail_waiting.visibility=View.GONE
                    }

                }
                else{
                    Log.v("TAG","값 전달 잘못")
                }
            }

            override fun onFailure(call: Call<GetEventSearch>?, t: Throwable?) {
                Log.v("TAG", "통신 실패")
            }
        })

    }

    //이벤트 참여 통신
    fun postJoinEvent(){
        var postJoinResponse = networkService.postJoinEvent("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkxOTYxMzN9.OrlfMuYaMa2SqrXGcHlDRmttGOC1z7DiROKD4dsz2Ds",intent.getIntExtra("eventId",0)) // 네트워크 서비스의 getContent 함수를 받아옴
        postJoinResponse.enqueue(object : Callback<PostJoinEvent> {
            override fun onResponse(call: Call<PostJoinEvent>, response: Response<PostJoinEvent>) {
                Log.v("TAG", "이벤트 참여 승인 POST 통신 성공")
                if (response.isSuccessful) {
                    Log.v("TAG", "이벤트 참여 승인 신청 성공")
                    Log.v("TAG", "status = " + response.body()!!.status)
                    Log.v("TAG", "message = " + response.body()!!.message)

                }
                else{
                    Log.v("TAG","값 전달 잘못")
                }
            }

            override fun onFailure(call: Call<PostJoinEvent>?, t: Throwable?) {
                Log.v("TAG", "통신 실패")
            }
        })

    }
}
