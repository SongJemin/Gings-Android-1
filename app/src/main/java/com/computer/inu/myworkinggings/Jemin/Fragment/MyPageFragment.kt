package com.computer.inu.myworkinggings.Jemin.Fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.computer.inu.myworkinggings.Jemin.Activity.PasswdModifyActivity
import com.computer.inu.myworkinggings.Jemin.Data.BoardItem
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetBoardResponse
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetOtherInformResponse
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Activity.ProfileSettingMenuActivity
import com.computer.inu.myworkinggings.Seunghee.Adapter.BoardRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_home_board.*
import kotlinx.android.synthetic.main.fragment_my_page.*
import kotlinx.android.synthetic.main.fragment_my_page.view.*
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageFragment : Fragment() {

    lateinit var requestManager: RequestManager
    lateinit var networkService : NetworkService
    var field : String = ""
    var status : String = ""
    var coworkingEnabled : Int = 0
    var checkFlag : Int = 0

    // 처음 프래그먼트 추가
    fun addFragment(fragment : Fragment){
        val fm = childFragmentManager
        val transaction = fm.beginTransaction()
        val myIntroFragment = MypageIntroFragment()
        val bundle = Bundle()
        bundle.putString("field", field)
        Log.v("asdf", "보내는필드 = " + field)
        bundle.putString("status", status)
        bundle.putInt("coworkingEnabled", coworkingEnabled)
        myIntroFragment.setArguments(bundle)

        transaction.add(R.id.mypage_content_layout, myIntroFragment)
        transaction.commit()
    }

    // 프래그먼트 교체
    fun replaceFragment(fragment: Fragment, checkFlag : Int) {
        val fm = childFragmentManager
        val transaction = fm.beginTransaction()

        if(checkFlag == 0){
            val myIntroFragment = MypageIntroFragment()
            val bundle = Bundle()
            bundle.putString("field", field)
            Log.v("asdf", "보내는필드 = " + field)
            bundle.putString("status", status)
            bundle.putInt("coworkingEnabled", coworkingEnabled)
            myIntroFragment.setArguments(bundle)
            transaction.replace(R.id.mypage_content_layout, myIntroFragment)
            transaction.commit()
        }
        else{
            transaction.replace(R.id.mypage_content_layout, fragment)
            transaction.commit()
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v : View = inflater.inflate(R.layout.fragment_my_page,container,false)
        v.mypage_act_view.visibility = View.INVISIBLE

        requestManager = Glide.with(this)
        requestManager.load("http://www.trinityseoul.com/uploads/8/7/6/4/87640636/art-talk-20_orig.jpg").into(v.mypage_background_img)

        v.mypage_act_btn.setTextColor(Color.parseColor("#bcc5d3"))
        v.mypage_intro_btn.setTextColor(Color.parseColor("#b0caea"))
        getOtherPage()

        // '소개' 클릭 시
        v.mypage_intro_btn.setOnClickListener {
            Log.v("asdf","소개 클릭")
            mypage_act_btn.isSelected = false
            mypage_intro_btn.isSelected = true
            mypage_act_btn.setTextColor(Color.parseColor("#bcc5d3"))
            mypage_intro_btn.setTextColor(Color.parseColor("#b0caea"))
            mypage_intro_view.setVisibility(View.VISIBLE)
            mypage_act_view.setVisibility(View.INVISIBLE)
            checkFlag=0

            replaceFragment(MypageIntroFragment(), checkFlag)
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
            checkFlag=1

            replaceFragment(MypageActFragment(), checkFlag)
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

    fun getOtherPage() {
        networkService = ApplicationController.instance.networkService
        var getOtherInformResponse = networkService.getOtherPageInform("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg",1) // 네트워크 서비스의 getContent 함수를 받아옴
        getOtherInformResponse.enqueue(object : Callback<GetOtherInformResponse> {
            override fun onResponse(call: Call<GetOtherInformResponse>?, response: Response<GetOtherInformResponse>?) {
                Log.v("TAG", "타인페이지 서버 통신 연결")
                if (response!!.isSuccessful) {
                    Log.v("TAG", "타인페이지 서버 통신 연결 성공")
                    if(response!!.body()!!.message == "자격 없음"){
                        iv_btn_my_page_setting.visibility = View.GONE
                        iv_btn_other_page_close.visibility = View.VISIBLE
                    }
                    else{
                        iv_btn_other_page_close.visibility = View.GONE
                        iv_btn_my_page_setting.visibility = View.VISIBLE
                    }
                    Log.v("asdf","응답 바디 = " + response.body().toString())
                    mypage_name_tv.text = response.body()!!.data.name
                    mypage_job_tv.text = response.body()!!.data.job
                    mypage_team_tv.text = response.body()!!.data.company
                    mypage_region_tv.text = response.body()!!.data.region
                    field = response.body()!!.data.field!!
                    status = response.body()!!.data.status!!

                    if(response.body()!!.data.coworkingEnabled == true){
                        coworkingEnabled = 1
                    }
                    else{
                        coworkingEnabled = 0
                    }


                    for(i in 0 .. response.body()!!.data.keywords.size-1){
                        if(i == 0){
                            mypage_keyword_tv.text = "#"+ response.body()!!.data.keywords[i].content
                        }
                        else{
                            mypage_keyword_tv.append("    #" + response.body()!!.data.keywords[i].content)
                        }
                    }
                    addFragment(MypageIntroFragment())
                }
            }

            override fun onFailure(call: Call<GetOtherInformResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })
    }
}