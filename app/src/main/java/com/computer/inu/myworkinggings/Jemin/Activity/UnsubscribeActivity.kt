package com.computer.inu.myworkinggings.Jemin.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.computer.inu.myworkinggings.Moohyeon.Activity.LoginActivity
import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Post.DeleteAccountResponse
import com.computer.inu.myworkinggings.Seunghee.db.SharedPreferenceController
import kotlinx.android.synthetic.main.activity_unsubscribe.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UnsubscribeActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unsubscribe)

        // 테스트 연결
        unsubscribe_confirm_btn.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)

            deleteAccountResponse()
        }
    }

    fun deleteAccountResponse() {
        val deleteBoardResponse: Call<DeleteAccountResponse> = networkService.deleteAccountResponse("application/json",
                SharedPreferenceController.getAuthorization(this))

        deleteBoardResponse.enqueue(object : Callback<DeleteAccountResponse> {
            override fun onFailure(call: Call<DeleteAccountResponse>, t: Throwable) {
                Log.e("sign up fail", t.toString())
                toast("탈퇴실패")
            }

            //통신 성공 시 수행되는 메소드
            override fun onResponse(call: Call<DeleteAccountResponse>, response: Response<DeleteAccountResponse>) {
                if (response.isSuccessful) {
                    toast("탈퇴")

                    finish()
                }
            }
        })
    }

}
