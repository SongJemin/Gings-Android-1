package com.computer.inu.myworkinggings

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {

    private val TAG = "IDService"

    override fun onTokenRefresh() {
        // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "Refreshed token: " + refreshedToken!!)
        //여기서는 토큰을 확인하고 획득한 토큰으로 여타 작업 예를 들어 웹서버에 토큰을 저장하는등 개별 동작 가능
        sendRegistrationToServer(refreshedToken)
    }

    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.

        //토큰을 웹서버로 보내 MYSQL DB에 저장하도록 하는 동작을 추가 하도록 하자.
        //여기는 매번 실행되는게 아닌듯 하다. 최초 앱이 설치될때 한번 수행되는듯 ...토큰은 한 기기에 하나만
        //OkHttp와 PHP, MYSQL

    }
}