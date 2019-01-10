package com.computer.inu.myworkinggings.Moohyeon.Activity

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.computer.inu.myworkinggings.Hyunjin.Get.GetVerifyNumberRequest
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetEmailRedundancyResponse
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import kotlinx.android.synthetic.main.activity_sign_up2.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class SignUp2Activity : AppCompatActivity() {
    var name : String = String()
    var password: String = String()
    var password_check: String = String()
    var email : String = String()
    val Passwrod_PATTERN = "^(?=.*[a-zA-Z]+)(?=.*[!@#$%^*+=-]|.*[0-9]+).{7,16}$"
    var status : String = ""
    var message : String = ""
    lateinit var networkService : NetworkService

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up2)
        tv_sign_up2_available_pw.visibility = View.INVISIBLE
        tv_sign_up2_available_email.visibility = View.GONE
        tv_sign_up2_confirm_number_send_message.isEnabled = false
        tv_sign_up2_confirm_number_send_message.setBackgroundColor(Color.parseColor("#FF9DA3A4"))

        networkService = ApplicationController.instance.networkService

        tv_sign_up2_overlap_check.setOnClickListener {
            getEmailRedundancy()
        }

        tv_sign_up2_confirm_number_send_message.setOnClickListener {

            var post_check = 0
            name = et_sign_up2_name.text.toString()
            email = et_sign_up2_email.text.toString()
            password = et_sign_up2_pw.text.toString()
            password_check = et_sign_up2_re_pw.text.toString()

            if (email.length > 0 && password.length > 0 && password_check.length > 0 && name.length > 0) {
                if (!Passwordvalidate(password)) {
                    post_check = 1
                    tv_sign_up2_available_pw.setText("영문자,숫자 조합 7글자 이상 입력해주세요.")
                    tv_sign_up2_available_pw.setVisibility(View.VISIBLE)
                    tv_sign_up2_available_pw.setTextColor(Color.parseColor("#ff6464"))
                }
                if (!password.equals(password_check)) {
                    post_check = 1
                    tv_sign_up2_available_pw.setText("일치하지 않습니다.")
                    tv_sign_up2_available_pw.setVisibility(View.VISIBLE)
                    tv_sign_up2_available_pw.setTextColor(Color.parseColor("#ff6464"))
                }
                if (post_check != 1) {
                      //여기다가 이메일 전송 하면된다!!!!~~
                    getVerifyNumberData()
                }
            }else {
                toast("정보를 모두 입력해주세요.")
            }
        }


    }

    fun Passwordvalidate(pw: String): Boolean {
        var pattern = Pattern.compile(Passwrod_PATTERN)
        var matcher = pattern.matcher(pw)
        return matcher.matches()
    }

    fun getVerifyNumberData() {
        var getVerifyNumberDataResponse = networkService.getVerifyNumberData("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg", et_sign_up2_email.text.toString()) // 네트워크 서비스의 getContent 함수를 받아옴
        getVerifyNumberDataResponse.enqueue(object : Callback<GetVerifyNumberRequest> {
            override fun onResponse(call: Call<GetVerifyNumberRequest>?, response: Response<GetVerifyNumberRequest>?) {
                Log.v("TAG", "GET 통신 성공")
                if (response!!.isSuccessful) {
             var token =response.headers().toString()
                    startActivity(intentFor<SignUp3Activity>("name" to name ,"password" to password,"token" to token))
                }
            }

            override fun onFailure(call: Call<GetVerifyNumberRequest>?, t: Throwable?) {
                Log.v("TAG", "통신 실패")
            }
        })
    }
    fun getEmailRedundancy() {
        networkService = ApplicationController.instance.networkService
        var getProjectResponse = networkService.getEmailRedundancyResponse(et_sign_up2_email.text.toString()) // 네트워크 서비스의 getContent 함수를 받아옴
        getProjectResponse.enqueue(object : Callback<GetEmailRedundancyResponse> {
            override fun onResponse(call: Call<GetEmailRedundancyResponse>?, response: Response<GetEmailRedundancyResponse>?) {
                Log.v("TAG", "GET 통신 성공")
                if (response!!.isSuccessful) {

                    Log.v("TAG", "이메일 중복 확인")
                    message = response.body()!!.message!!
                    if(message == "이미 등록된 이메일입니다"){
                      toast("이미 있는 아이디입니다.")  // 이미 있는 아이디로 토스트 띄우기
                        tv_sign_up2_available_email.setText("이미 등록된 이메일 입니다.")
                        tv_sign_up2_available_email.setVisibility(View.VISIBLE)
                        tv_sign_up2_confirm_number_send_message.isEnabled = false
                        tv_sign_up2_confirm_number_send_message.setBackgroundColor(Color.parseColor("#FF9DA3A4"))
                    }
                    else{
                      toast("사용 가능한 아이디입니다.")    // 사용 가능한 아이디입니다 토스트 띄우기
                        tv_sign_up2_available_email.setText("사용 가능한 이메일 입니다.")
                        tv_sign_up2_available_email.setTextColor(Color.parseColor("#64dfff"))
                        tv_sign_up2_confirm_number_send_message.isEnabled = true
                        tv_sign_up2_confirm_number_send_message.setBackgroundColor(Color.parseColor("#f7746b"))

                    }

                    Log.v("TAG", "이메일 중복 확인 stats = " + status)
                    Log.v("TAG", "이메일 중복 확인 message = " + message)
                }

            }

            override fun onFailure(call: Call<GetEmailRedundancyResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패")
            }
        })
    }

}
