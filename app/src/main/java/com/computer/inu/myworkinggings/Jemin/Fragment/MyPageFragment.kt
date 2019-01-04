package com.computer.inu.myworkinggings.Jemin.Fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.computer.inu.myworkinggings.Jemin.Activity.PasswdModifyActivity
import com.computer.inu.myworkinggings.Jemin.Data.GuestBoardItem
import com.computer.inu.myworkinggings.Moohyeon.Data.UserPageData
import com.computer.inu.myworkinggings.Moohyeon.get.GetGuestBoardResponse
import com.computer.inu.myworkinggings.Moohyeon.get.GetMypageResponse
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Activity.ProfileSettingMenuActivity
import kotlinx.android.synthetic.main.fragment_my_page.*
import kotlinx.android.synthetic.main.fragment_my_page.view.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.bumptech.glide.Glide
class MyPageFragment : Fragment() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    lateinit var requestManager: RequestManager
    var keword : String= "";


    // 처음 프래그먼트 추가
    fun addFragment(fragment : Fragment){
        val fm = childFragmentManager
        val transaction = fm.beginTransaction()
        transaction.add(R.id.mypage_content_layout, fragment)
        transaction.commit()
    }

    // 프래그먼트 교체
    fun replaceFragment(fragment: Fragment) {
        val fm = childFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.mypage_content_layout, fragment)
        transaction.commit()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v : View = inflater.inflate(R.layout.fragment_my_page,container,false)
        v.mypage_act_view.visibility = View.INVISIBLE
        addFragment(MypageIntroFragment())
        requestManager = Glide.with(this)
        requestManager.load("http://www.trinityseoul.com/uploads/8/7/6/4/87640636/art-talk-20_orig.jpg").into(v.mypage_background_img)

        v.mypage_act_btn.setTextColor(Color.parseColor("#bcc5d3"))
        v.mypage_intro_btn.setTextColor(Color.parseColor("#b0caea"))
        getUserPagePost()
        // '소개' 클릭 시
        v.mypage_intro_btn.setOnClickListener {
            Log.v("asdf","소개 클릭")
            mypage_act_btn.isSelected = false
            mypage_intro_btn.isSelected = true
            mypage_act_btn.setTextColor(Color.parseColor("#bcc5d3"))
            mypage_intro_btn.setTextColor(Color.parseColor("#b0caea"))
            mypage_intro_view.setVisibility(View.VISIBLE)
            mypage_act_view.setVisibility(View.INVISIBLE)
            replaceFragment(MypageIntroFragment())
        }

        // '활동' 클릭 시
        v.mypage_act_btn.setOnClickListener {
            Log.v("asdf","활동 클릭")
            mypage_act_btn.isSelected = true
            mypage_intro_btn.isSelected = false
            mypage_intro_btn.setTextColor(Color.parseColor("#bcc5d3"))
            mypage_act_btn.setTextColor(Color.parseColor("#b0caea"))
            mypage_act_view.setVisibility(View.VISIBLE)
            mypage_intro_view.setVisibility(View.INVISIBLE)
            replaceFragment(MypageActFragment())
        }

        v.iv_btn_my_page_setting.setOnClickListener {
            startActivity<ProfileSettingMenuActivity>()
        }

        // 테스트 연결
        v.mypage_background_img.setOnClickListener {
            var intent = Intent(activity, PasswdModifyActivity::class.java)
            startActivity(intent)
        }

        return v
    }
    fun getUserPagePost(){
        var getMypageResponse: Call<GetMypageResponse> = networkService.getMypageResponse("application/json","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg")
        getMypageResponse.enqueue(object : Callback<GetMypageResponse> {
            override fun onResponse(call: Call<GetMypageResponse>?, response: Response<GetMypageResponse>?) {
                Log.v("TAG", "보드 서버 통신 연결")
                if (response!!.isSuccessful) {
                    val temp : UserPageData = response.body()!!.data

                    mypage_name_tv.text=temp.name
                    mypage_job_tv.text=temp.job
                    mypage_team_tv.text=temp.company
                    mypage_region_tv.text=temp.region
                    Glide.with(ctx).load(temp.image).into(mypage_background_img)

                    for(i in 0..temp.keywords.size-1)
                    {
                        keword += (temp.keywords[i].content.toString() + " ")
                    }
                    mypage_myposition_tv.text = keword
                }


            }

            override fun onFailure(call: Call<GetMypageResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })
    }
}