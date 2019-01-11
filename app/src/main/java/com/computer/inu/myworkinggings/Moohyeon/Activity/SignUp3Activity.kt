package com.computer.inu.myworkinggings.Moohyeon.Activity

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import com.computer.inu.myworkinggings.Moohyeon.post.PostSignUpResponse
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_sign_up2.*
import kotlinx.android.synthetic.main.activity_sign_up3.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUp3Activity : AppCompatActivity() {
    var message : String = ""
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up3)

        tv_sign_up3_sign_up_complete.setOnClickListener {
            tv_sign_up3_sign_up_complete.isEnabled=false
            val delayHandler = Handler()
            delayHandler.postDelayed(Runnable {
                tv_sign_up3_sign_up_complete.isEnabled=true
            }, 3000)
            SignUpPost()
            startActivity<LoginActivity>()
            finish()
        }
    }
    private fun SignUpPost() {
//Json 형식의 객체 만들기
        var jsonObject = JSONObject()
        jsonObject.put("name", intent.getStringExtra("name"))
        jsonObject.put("pwd", intent.getStringExtra("password"))
        jsonObject.put("authNumber", et_sign_up3_confirm_number.text.toString())

//Gson 라이브러리의 Json Parser을 통해 객체를 Json으로!
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postSignUpResponse: Call<PostSignUpResponse> =
                networkService.postSignUpResponse("application/json",intent.getStringExtra("token"), gsonObject)
        postSignUpResponse.enqueue(object : Callback<PostSignUpResponse> {
            override fun onFailure(call: Call<PostSignUpResponse>, t: Throwable) {
                toast(intent.getStringExtra("token"))
                          toast("알수 없는 오류")
            }
            //통신 성공 시 수행되는 메소드
            override fun onResponse(call: Call<PostSignUpResponse>, response: Response<PostSignUpResponse>) {
                if (response.isSuccessful) {
                    message = response.body()!!.message!!
                    if(message == "회원 가입 성공"){
                        toast("회원 가입 성공")
                    }
                    else{
                        toast("회원 가입 실패")
                    }
                }
            }
        })
    }
}
