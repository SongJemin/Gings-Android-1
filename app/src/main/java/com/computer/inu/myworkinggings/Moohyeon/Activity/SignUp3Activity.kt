package com.computer.inu.myworkinggings.Moohyeon.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.computer.inu.myworkinggings.Moohyeon.post.PostSignUpResponse
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_sign_up3.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUp3Activity : AppCompatActivity() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up3)
        tv_sign_up3_sign_up_complete.setOnClickListener {
            SignUpPost()
        }
    }
    private fun SignUpPost() {
//EditText에 있는 값 받기
       /* val input_name: String = et_sign_up_act_name.text.toString()
        val input_pw: String = et_sign_up_act_pw.text.toString()
        val input_email: String = et_sign_up_act_email.text.toString()
        val input_part: String = et_sign_up_act_part.text.toString()*/
//Json 형식의 객체 만들기
        var jsonObject = JSONObject()
        jsonObject.put("name", "김무현")
        jsonObject.put("pwd", "RLASJD1414")
        jsonObject.put("authNumber", 4096)

//Gson 라이브러리의 Json Parser을 통해 객체를 Json으로!
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postSignUpResponse: Call<PostSignUpResponse> =
                networkService.postSignUpResponse("application/json","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg", gsonObject)
        postSignUpResponse.enqueue(object : Callback<PostSignUpResponse> {
            override fun onFailure(call: Call<PostSignUpResponse>, t: Throwable) {

            }
            //통신 성공 시 수행되는 메소드
            override fun onResponse(call: Call<PostSignUpResponse>, response: Response<PostSignUpResponse>) {
                if (response.isSuccessful) {
                 toast("가입성공")
                }
            }
        })
    }
}
