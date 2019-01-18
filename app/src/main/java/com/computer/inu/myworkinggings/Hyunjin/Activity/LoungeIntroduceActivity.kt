package com.computer.inu.myworkinggings.Hyunjin.Activity

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.computer.inu.myworkinggings.Hyunjin.Adapter.LoungeEventDataRecyclerViewAdapter
import com.computer.inu.myworkinggings.Hyunjin.Get.*
import com.computer.inu.myworkinggings.Hyunjin.Post.PostClubSignUp
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import kotlinx.android.synthetic.main.activity_lounge_introduce.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoungeIntroduceActivity : AppCompatActivity() {


    lateinit var networkService : NetworkService
    //라운지 일정부분 데이터 통신을 위해 이벤트 리사이클러 어뎁터 연결
    lateinit var LoungeEventDataRecyclerViewAdapter: LoungeEventDataRecyclerViewAdapter

    //통신에 대한 배열데이터를 담을 데이터리스트를 생성
    var dataList: ArrayList<EventDetailData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lounge_introduce)

        networkService = ApplicationController.instance.networkService


        getDetailSearchClub()
        rl_lounge_introuce_introudeuce_bar.setOnClickListener {
            ll_lounge_introduce_introduce_layout.visibility=View.VISIBLE
            ll_lounge_introduce_introduce_schedule.visibility=View.GONE
            //tv_lounge_introduce.setTextColor(Color.parseColor("#000000"))
            tv_lounge_introduce.setTypeface(null, Typeface.BOLD);
            tv_lounge_calendar.setTypeface(null, Typeface.NORMAL);
        }

        rl_lounge_introuce_event_bar.setOnClickListener {
            ll_lounge_introduce_introduce_layout.visibility= View.GONE
            ll_lounge_introduce_introduce_schedule.visibility=View.VISIBLE
            //tv_lounge_calendar.setTextColor(Color.parseColor("#000000"))
            tv_lounge_calendar.setTypeface(null, Typeface.BOLD);
            tv_lounge_introduce.setTypeface(null, Typeface.NORMAL);

        }
     //var clubId =  intent.getIntExtra("clubId",0)
        btn_lounge_back.setOnClickListener{
            finish()
        }
        btn_lounge_introduce_signup.setOnClickListener{
            btn_lounge_introduce_signup.visibility=View.GONE
            btn_lounge_introduce_signup_waiting.visibility=View.VISIBLE
            postClubSignUp()
        }

    }

    //클럽 가입 통신
    fun postClubSignUp() {
        Log.v("asd","클럽가입")
        var postClubSignUpResponse = networkService.postClubSignUp("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkxOTYxMzN9.OrlfMuYaMa2SqrXGcHlDRmttGOC1z7DiROKD4dsz2Ds",intent.getIntExtra("clubId",1)) // 네트워크 서비스의 getContent 함수를 받아옴
        postClubSignUpResponse.enqueue(object : Callback<PostClubSignUp> {
            override fun onResponse(call: Call<PostClubSignUp>?, response: Response<PostClubSignUp>?) {
                Log.v("TAG", "POST 클럽 가입 통신 성공")
                if (response!!.isSuccessful) {
                    Log.v("TAG", "클럽 가입 승인 신청 성공")
                    Log.v("TAG", "status = " + response.body()!!.status)
                    Log.v("TAG", "message = " + response.body()!!.message)
                    var message = response.body()!!.message!!

                    if(message == "가입완료"){
                        btn_lounge_introduce_signup_waiting.visibility=View.GONE
                    }
                    else{

                    }

                }
                else{
                    Log.v("TAG","값 전달 잘못")
                }
            }

            override fun onFailure(call: Call<PostClubSignUp>?, t: Throwable?) {
                Log.v("TAG", "통신 실패")
            }
        })
    }

    //클럽 상세 조회 통신
    fun getDetailSearchClub(){
        Log.v("TAG","연결안됨")
        var getDetailSearchClubResponse = networkService.getDetailSearchClub("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkxOTYxMzN9.OrlfMuYaMa2SqrXGcHlDRmttGOC1z7DiROKD4dsz2Ds",intent.getIntExtra("clubId",0)) // 네트워크 서비스의 getContent 함수를 받아옴
        getDetailSearchClubResponse.enqueue(object : Callback<GetDetailSearchClub> {
            override fun onResponse(call: Call<GetDetailSearchClub>, response: Response<GetDetailSearchClub>) {
                Log.v("TAG", "클럽 상세 조회 GET 통신 성공")
                if (response.isSuccessful) {
                    Log.v("TAG", "클럽 정보 조회 성공")
                    Log.v("TAG", "status = " + response.body()!!.status)
                    Log.v("TAG", "message = " + response.body()!!.message)
                    Log.v("TAG", "body = " + response.body()!!.toString())

                    if(response.body()!!.data.event != null) {
                        dataList = response.body()!!.data.event
                    }

                    // 일정 부분 데이터 연결 부분
                   LoungeEventDataRecyclerViewAdapter = LoungeEventDataRecyclerViewAdapter(ctx, intent.getIntExtra("clubId",0),dataList)
                    rv_lounge_frag_lounge_event_list.adapter = LoungeEventDataRecyclerViewAdapter
                    rv_lounge_frag_lounge_event_list.layoutManager = LinearLayoutManager(ctx, LinearLayout.HORIZONTAL, false)

                    Log.v("TAG","일정 부분 통과")

                    // 소개 부분 데이터 연결 부분
                    tv_rl_lounge_introdata.text = response.body()!!.data.introImg
                    rl_top_bar_lounge_text.text = response.body()!!.data.title
                    tv_lounge_intro_title.text = response.body()!!.data.title

                    Log.v("TAG","소개 부분 통과")


                    response.body()!!.data

                    Glide.with(ctx).load(response.body()!!.data.backImg).centerCrop().into(rl_iv_lounge_backImg)

                    if(response.body()!!.data.userStatus=="가입하기"){
                        btn_lounge_introduce_signup.visibility=View.VISIBLE

                    }
                    else if(response.body()!!.data.userStatus=="승인완료"){
                        btn_lounge_introduce_signup_waiting.visibility=View.GONE
                    }

                }
                else{
                    Log.v("TAG","값 전달 잘못")
                }
            }

            override fun onFailure(call: Call<GetDetailSearchClub>?, t: Throwable?) {
                Log.v("TAG", "통신 실패")
            }
        })

    }


}
