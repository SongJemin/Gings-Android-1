package com.computer.inu.myworkinggings.Moohyeon.Activity

import android.app.Activity
import android.content.SharedPreferences
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.computer.inu.myworkinggings.Jemin.Activity.MainActivity
import android.util.Log
import android.widget.Toast
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
import android.content.pm.PackageManager
import android.util.Base64
import com.kakao.util.helper.Utility.getPackageInfo
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class LoginActivity : AppCompatActivity() {

    var userID : Int = 0

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val boardID = intent.getIntExtra("BoardId",-1)
        if(boardID> 0)
        {
            Log.v("카카오로그인", "으로들어옴")

            tv_login_login_button.setOnClickListener {
                startActivity<DetailBoardActivity>("BoardId" to boardID)
            }

        }else{
            //***로그인 통신***
            Log.v("카카오로그인", "으로들어오지않음")

            tv_login_login_button.setOnClickListener {
                startActivity<MainActivity>()
                //sendLink()
            }

        }


        //startActivity<DetailBoardActivity>("BoardId" to boardID)


        tv_login_join_us.setOnClickListener {
            startActivity<SignUp1Activity>()
        }
        tv_login_about_gings.setOnClickListener {
            startActivity<DetailBoardActivity>()
        }

        //***로그인 통신***
        tv_login_login_button.setOnClickListener {
            getLoginResponse()
        }

            //startActivity<BottomNaviActivity>()
            //getLoginResponse()
        //startActivity<BottomNaviActivity>()

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

                        if(response.body()!!.message == "로그인 성공"){
                            userID = response.body()!!.data.userId
                            var pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
                            var editor : SharedPreferences.Editor = pref.edit()
                            editor.putInt("userID", userID) //userID란  key값으로 userID 데이터를 저장한다.
                            Log.v("LoginActivity", "유저 번호 = " + userID)
                            editor.commit()
                            Log.v("ttt", "Login-!-!-!")
                            startActivity<MainActivity>()
                        }
                        else{
                            Toast.makeText(applicationContext,"회원 정보가 틀렸습니다.", Toast.LENGTH_LONG).show()
                        }


                        //자동 로그인
                        /*val firstLogIn = response.body()!!.data.firstLogIn

                        if(cb_login_auto_check_box.isChecked){
                            //SharedPreferenceController.setAuthorization(this@LoginActivity, firstLogIn)
                            //toast(SharedPreferenceController.getAuthorization(this@LoginActivity))
                        }
*/
                        //startActivity<BottomNaviActivity>()
                    }
                    else{
                        Toast.makeText(applicationContext,"회원 정보가 틀렸습니다.", Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
        else{
            Toast.makeText(applicationContext,"빈칸 없이 입력해주세요.", Toast.LENGTH_LONG).show()
        }

    }

}
