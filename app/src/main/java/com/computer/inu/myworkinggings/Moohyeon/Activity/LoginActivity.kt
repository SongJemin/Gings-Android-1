package com.computer.inu.myworkinggings.Moohyeon.Activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.computer.inu.myworkinggings.Jemin.Activity.MainActivity

import android.util.Log

import com.computer.inu.myworkinggings.Network.ApplicationController
import com.computer.inu.myworkinggings.Network.NetworkService
import com.computer.inu.myworkinggings.R
import com.computer.inu.myworkinggings.Seunghee.Post.PostLogInResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Base64.NO_WRAP
import android.provider.SyncStateContract.Helpers.update
import android.content.pm.PackageManager
import android.content.pm.PackageInfo
import android.support.v4.app.FragmentActivity
import android.util.Base64
import com.kakao.kakaolink.v2.KakaoLinkResponse
import com.kakao.kakaolink.v2.KakaoLinkService
import com.kakao.message.template.ButtonObject
import com.kakao.message.template.ContentObject
import com.kakao.message.template.FeedTemplate
import com.kakao.message.template.LinkObject
import com.kakao.network.ErrorResult
import com.kakao.network.callback.ResponseCallback
import com.kakao.util.helper.Utility.getPackageInfo
import com.kakao.util.helper.log.Logger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class LoginActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_login_join_us.setOnClickListener {
            startActivity<SignUp1Activity>()
        }
        tv_login_about_gings.setOnClickListener {
            startActivity<DetailBoardActivity>()
        }

        //***로그인 통신***
        tv_login_login_button.setOnClickListener {
            startActivity<MainActivity>()
            //sendLink()
        }

        //startActivity<BottomNaviActivity>()
        getLoginResponse()

        //getKeyHash(applicationContext)
        //Log.v("카카오",getKeyHash(applicationContext))
    }

    fun getKeyHash(context: Context): String? {
        val packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES) ?: return null

        for (signature in packageInfo!!.signatures) {
            try {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                return Base64.encodeToString(md.digest(), Base64.NO_WRAP)
            } catch (e: NoSuchAlgorithmException) {

                //Log.w(FragmentActivity.TAG, "Unable to get MessageDigest. signature=$signature", e)
            }

        }
        return null
    }
/*
    // 카카오링크 API 실행
    private fun sendLink() {
        val params = FeedTemplate
                .newBuilder(ContentObject.newBuilder("카카오~!",
                        "http://www.trinityseoul.com/uploads/8/7/6/4/87640636/art-talk-20_orig.jpg",
                        LinkObject.newBuilder().setWebUrl("")
                                .setMobileWebUrl("").build())
                        .setDescrption("깅스에 초대받으셨습니다.")
                        .build())

                .addButton(ButtonObject("깅스 앱으로 열기", LinkObject.newBuilder()
                        //.setWebUrl("'https://developers.kakao.com")
                        //.setAndroidExecutionParams("roomIDValue="+roomIDValue)
                        .build()))
                .build()

        KakaoLinkService.getInstance().sendDefault(applicationContext, params, object : ResponseCallback<KakaoLinkResponse>() {

            override fun onFailure(errorResult: ErrorResult) {
                Logger.e(errorResult.toString())
            }
            override fun onSuccess(result: KakaoLinkResponse) {}
        })
    }*/



    //로그인 통신
    private fun getLoginResponse() {
        if (et_login_id.text.toString().isNotEmpty() && et_login_pw.text.toString().isNotEmpty()) {
            val input_email = et_login_id.text.toString()
            val input_pw = et_login_pw.text.toString()
            val jsonObject: JSONObject = JSONObject()
            jsonObject.put("email", input_email)
            jsonObject.put("pwd", input_pw)
            val gsonObject: JsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject


            val postLogInResponse = networkService.postLoginResponse("application/json", gsonObject)

            postLogInResponse.enqueue(object : Callback<PostLogInResponse> {

                override fun onFailure(call: Call<PostLogInResponse>, t: Throwable) {
                    Log.e("Login fail", t.toString())
                }

                override fun onResponse(call: Call<PostLogInResponse>, response: Response<PostLogInResponse>) {
                    if (response.isSuccessful) {

                        Log.v("ttt", "Login-!-!-!")

                        //자동 로그인
                        /*val firstLogIn = response.body()!!.data.firstLogIn

                        if(cb_login_auto_check_box.isChecked){
                            //SharedPreferenceController.setAuthorization(this@LoginActivity, firstLogIn)
                            //toast(SharedPreferenceController.getAuthorization(this@LoginActivity))
                        }
*/
                        startActivity<BottomNaviActivity>()
                    }
                }
            })
        }

    }

}
