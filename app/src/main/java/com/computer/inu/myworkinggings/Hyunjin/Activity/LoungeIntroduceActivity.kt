package com.computer.inu.myworkinggings.Hyunjin.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.View
import com.computer.inu.myworkinggings.Hyunjin.Fragment.LoungeEventFragment
import com.computer.inu.myworkinggings.Hyunjin.Get.GetVerifyNumberRequest
import com.computer.inu.myworkinggings.Hyunjin.Post.PostClubSignUp
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import kotlinx.android.synthetic.main.activity_lounge_introduce.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoungeIntroduceActivity : AppCompatActivity() {
    lateinit var networkService : NetworkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lounge_introduce)

        networkService = ApplicationController.instance.networkService
        addFragment(LoungeEventFragment())

        rl_lounge_introuce_introudeuce_bar.setOnClickListener {
            ll_lounge_introduce_introduce_layout.visibility=View.VISIBLE
            ll_lounge_introduce_introduce_schedule.visibility=View.GONE
        }

        rl_lounge_introuce_event_bar.setOnClickListener {
            ll_lounge_introduce_introduce_layout.visibility= View.GONE
            ll_lounge_introduce_introduce_schedule.visibility=View.VISIBLE
        }

        btn_lounge_back.setOnClickListener{
            finish()
            //replaceFragment(LoungeFragment())
        }
        btn_lounge_introduce.setOnClickListener{
            postClubSignUp()
        }


    }

    private fun addFragment(fragment : Fragment){
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_event_image, fragment)
        transaction.commit()
    }

    fun postClubSignUp() {
        Log.v("asd","클럽가입")
        var postClubSignUpResponse = networkService.postClubSignUp("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkxOTYxMzN9.OrlfMuYaMa2SqrXGcHlDRmttGOC1z7DiROKD4dsz2Ds",1) // 네트워크 서비스의 getContent 함수를 받아옴
        postClubSignUpResponse.enqueue(object : Callback<PostClubSignUp> {
            override fun onResponse(call: Call<PostClubSignUp>?, response: Response<PostClubSignUp>?) {
                Log.v("TAG", "POST 통신 성공")
                if (response!!.isSuccessful) {
                    Log.v("TAG", "클럽가입 통신 성공")
                    Log.v("TAG", "status = " + response.body()!!.status)
                    Log.v("TAG", "message = " + response.body()!!.message)

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

}
