package com.computer.inu.myworkinggings

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "MessagingService"

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        if (remoteMessage!!.notification != null) {
            // do with Notification payload...
            // remoteMessage.notification.body
            //알림 정보를 가지고 여기서 지지고 볶고 하라는 것이다. 포그라운드일때 수신? 그래서 추가 작업
            Log.d(TAG, "From: " + remoteMessage!!.from)
            Log.d(TAG, "${remoteMessage.notification?.body}")

            sendNotification(remoteMessage.notification?.body.toString())
        }

        if (remoteMessage.data.isNotEmpty()) {
            // do with Data payload...
            // remoteMessage.data
            Log.d(TAG, "${remoteMessage.data} : 이것은 data입니다.")

        }
    }

    fun sendNotification(messageBody : String){
        TODO()
    }
}

