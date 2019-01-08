package com.computer.inu.myworkinggings.Jemin.Activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.computer.inu.myworkinggings.Jemin.POST.PostResponse
import com.computer.inu.myworkinggings.Moohyeon.Activity.LoginActivity
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.R
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_password_confirm.*
import kotlinx.android.synthetic.main.dialog_password_confirm.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PasswordConfirmActivity : AppCompatActivity() {

    val networkService: com.computer.inu.myworkinggings.Network.NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_confirm)

        // 테스트 연결
        password_confirm_confirm_btn.setOnClickListener {
            patchPassword()
        }

    }

    protected fun showDialog() {
        var showDialog = Dialog(this)
        showDialog.setCancelable(true)

        val preferMemberDialogView = this!!.layoutInflater.inflate(R.layout.dialog_password_confirm, null)
        showDialog.setContentView(preferMemberDialogView)
        showDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        showDialog.tv_dialog_password_text3.setOnClickListener {
            var intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }
        try {

        } catch (e: Exception) {
        }
        showDialog.show()
    }

    private fun patchPassword() {
        val token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjksInJvbGUiOiJVU0VSIiwiaXNzIjoiR2luZ3MgVXNlciBBdXRoIE1hbmFnZXIiLCJleHAiOjE1NDkwODg1Mjd9.P7rYzg9pNtc31--pL8qGYkC7cx2G93HhaizWlvForfg"

        if (password_confirm_first_edit.text.toString().isNotEmpty() && password_confirm_second_edit.text.toString().isNotEmpty()) {

            val jsonObject: JSONObject = JSONObject()
            jsonObject.put("newPwd1", password_confirm_first_edit.text.toString())
            jsonObject.put("newPwd2", password_confirm_second_edit.text.toString())
            val gsonObject: JsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

            val patchPoasswordResponse = networkService.patchPassword(token, gsonObject)
            patchPoasswordResponse.enqueue(object : Callback<PostResponse> {

                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                    Log.e("Password Modify fail = ", t.toString())
                }
                override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                    if (response.isSuccessful) {
                        if(response.body()!!.message == "비밀번호 변경 성공"){
                            showDialog()
                        }
                        else if(response.body()!!.message == "두 비밀번호가 일치하지 않습니다."){
                            Toast.makeText(applicationContext,"두 비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })
        }
        else{
            Toast.makeText(applicationContext, "빈칸을 채워주세요", Toast.LENGTH_LONG).show()
        }

    }
}
