package com.computer.inu.myworkinggings.Jemin.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.computer.inu.myworkinggings.Jemin.Get.Response.GetPasswdConfirmResponse
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.R
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_passwd_modify.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PasswdModifyActivity : AppCompatActivity() {

    val networkService: com.computer.inu.myworkinggings.Network.NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passwd_modify)

        // 테스트 연결
        passwd_modify_confirm_btn.setOnClickListener {
            getCurrentPasswdConfirm()
        }
    }

    private fun getDetailedBoardResponse() {


        val jsonObject: JSONObject = JSONObject()
        jsonObject.put("oldPwd", passwd_modify_passwd_edit.text.toString())
        val gsonObject: JsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject


        val getPasswdConfirmResponse = networkService.getCurrentPasswordConfirm("Asdf", gsonObject)

        getPasswdConfirmResponse.enqueue(object : Callback<GetPasswdConfirmResponse> {
            override fun onFailure(call: Call<GetPasswdConfirmResponse>, t: Throwable) {
                Log.e("password confirm fail = ", t.toString())
            }

            override fun onResponse(call: Call<GetPasswdConfirmResponse>, response: Response<GetPasswdConfirmResponse>) {
                if (response.isSuccessful) {
                    if(response.body()!!.message == "비밀번호가 일치합니다."){
                        val intent = Intent(applicationContext, PasswordConfirmActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(applicationContext,"비밀번호가 틀렸습니다.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    //로그인 통신
    private fun getCurrentPasswdConfirm() {
        val token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg"

        if (passwd_modify_passwd_edit.text.toString().isNotEmpty()) {

            val jsonObject: JSONObject = JSONObject()
            jsonObject.put("oldPwd", "adsfasdf")
            val gsonObject: JsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

            val getPasswdConfirmResponse = networkService.getCurrentPasswordConfirm(token, gsonObject)
            getPasswdConfirmResponse.enqueue(object : Callback<GetPasswdConfirmResponse> {

                override fun onFailure(call: Call<GetPasswdConfirmResponse>, t: Throwable) {
                    Log.e("Login fail", t.toString())
                }

                override fun onResponse(call: Call<GetPasswdConfirmResponse>, response: Response<GetPasswdConfirmResponse>) {
                    if (response.isSuccessful) {
                        if(response.body()!!.message == "비밀번호가 일치합니다."){
                            val intent = Intent(applicationContext, PasswordConfirmActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(applicationContext,"비밀번호가 틀렸습니다.", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })
        }
        else{
            Toast.makeText(applicationContext, "비밀번호를 입력해주세요.", Toast.LENGTH_LONG).show()
        }

    }


}
