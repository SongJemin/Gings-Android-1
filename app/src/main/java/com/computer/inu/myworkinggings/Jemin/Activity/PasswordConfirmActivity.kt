package com.computer.inu.myworkinggings.Jemin.Activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import com.computer.inu.myworkinggings.Jemin.POST.PostResponse
import com.computer.inu.myworkinggings.Moohyeon.Activity.LoginActivity
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.db.SharedPreferenceController
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_password_confirm.*
import kotlinx.android.synthetic.main.activity_sign_up2.*
import kotlinx.android.synthetic.main.dialog_password_confirm.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class PasswordConfirmActivity : AppCompatActivity() {
    val Passwrod_PATTERN = "^(?=.*[a-zA-Z]+)(?=.*[!@#$%^*+=-]|.*[0-9]+).{7,16}$"
    val networkService: com.computer.inu.myworkinggings.Network.NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_confirm)

        // 테스트 연결
        password_confirm_confirm_btn.setOnClickListener {
            var post_check = 0
            if (password_confirm_first_edit.text.toString().isNotEmpty() && password_confirm_second_edit.text.toString().isNotEmpty()) {
                if (!Passwordvalidate(password_confirm_first_edit.text.toString())) {
                    post_check = 1
                  toast("영문자,숫자 조합 7글자 이상 입력해주세요.")

                }
                if (!password_confirm_first_edit.text.toString().equals(password_confirm_second_edit.text.toString())) {
                    post_check = 1
                    toast("일치하지 않습니다.")

                }
                if (post_check != 1) {
                    password_confirm_confirm_btn.isEnabled=false
                    val delayHandler = Handler()
                    delayHandler.postDelayed(Runnable {
                        password_confirm_confirm_btn.isEnabled=true
                    }, 3000)
                    patchPassword()
                }
            }else {
                toast("정보를 모두 입력해주세요.")
            }

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
            finish()
        }
        try {

        } catch (e: Exception) {
        }
        showDialog.show()
    }
    fun Passwordvalidate(pw: String): Boolean {
        var pattern = Pattern.compile(Passwrod_PATTERN)
        var matcher = pattern.matcher(pw)
        return matcher.matches()
    }
    private fun patchPassword() {
        val token = SharedPreferenceController.getAuthorization(this)

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
                            SharedPreferenceController.clearSPC(ctx)
                            SharedPreferenceController.AutoclearSPC(ctx)
                            SharedPreferenceController.userIdclearSPC(ctx)
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
