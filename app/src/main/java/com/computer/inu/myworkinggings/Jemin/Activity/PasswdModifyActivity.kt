package com.computer.inu.myworkinggings.Jemin.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.computer.inu.myworkinggings.Jemin.POST.PostResponse
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.db.SharedPreferenceController
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
            //var intent = Intent(applicationContext, PasswordConfirmActivity::class.java)
            //startActivity(intent)
            postDetailedBoardResponse()
        }
    }

    private fun postDetailedBoardResponse() {
        val token = SharedPreferenceController.getAuthorization(this)

        val jsonObject: JSONObject = JSONObject()
        jsonObject.put("oldPwd", passwd_modify_passwd_edit.text.toString())
        val gsonObject: JsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        val getPasswdConfirmResponse = networkService.postCurrentPasswordConfirm(token, gsonObject)

        getPasswdConfirmResponse.enqueue(object : Callback<PostResponse> {
            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Log.e("password confirm fail = ", t.toString())
            }

            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                if (response.isSuccessful) {
                    if(response.body()!!.message == "비밀번호가 일치합니다."){
                        val intent = Intent(applicationContext, PasswordConfirmActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(applicationContext,"비밀번호가 틀렸습니다.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }




}
