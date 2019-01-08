package com.computer.inu.myworkinggings.Firebase

import android.app.Activity
import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.Toast
import com.computer.inu.myworkinggings.Jemin.Activity.MainActivity
import com.computer.inu.myworkinggings.R


import com.google.firebase.messaging.RemoteMessage


import java.net.URL


/**
 * Created by Kyuhee on 2018-03-12.
 */


/**
 *
 * 이 서비스 클래스는 푸시 메세지를 받아서 처리하는 것을 구현합니다.
 * onMessageReceived 메서드에서는 푸시 메세지를 수신했을 때 호출이 되며, pushDataMap에는 푸시 메세지 내용이 담기게 됩니다.
 * sendNotification 메서드는 푸시 메세지를 알림으로 표현하는 처리를 담당하게 됩니다.
 * 해당 메서드는 자신의 로직에 맞게끔 구현하면 되며, 위는 예로 구현을 한 것입니다.
 */


class FirebaseMessagingService : com.google.firebase.messaging.FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage?) {


        Log.d(TAG, isAppRunning(this).toString())


        if (isAppRunning(this)) {           // 앱 포그라운드 실행중
            //            val intent = Intent(this, MainActivity::class.java)          // 로그인 화면으로 이동.
            //            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            //            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
            //            try {
            //                pendingIntent.send()
            //            } catch (e: PendingIntent.CanceledException) {
            //                e.printStackTrace()
            //            }


        } else {              // 앱 백그라운드 실행중
            Log.d(TAG, "app background running...")
            val intent = Intent(this, MainActivity::class.java)          // 로그인 화면으로 이동.
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


        val notification = remoteMessage!!.notification


        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)


        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
                //                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.color_push))
                .setContentTitle(notification!!.title)
                .setContentText(notification.body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVibrate(longArrayOf(300, 500, 300, 500))     //진동
                .setContentIntent(pendingIntent)


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val channelId = "cowalkerAlarm"
        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }


        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }


    companion object {


        private val TAG = "MyFirebaseMsgService"


        fun isAppRunning(context: Context): Boolean {                        // 어플이 실행 중인지 확인 하는 함수.
            val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val procInfos = activityManager.runningAppProcesses
            for (i in procInfos.indices) {
                if (procInfos[i].processName == context.packageName) {
                    return true
                }
            }
            return false
        }
    }


}
