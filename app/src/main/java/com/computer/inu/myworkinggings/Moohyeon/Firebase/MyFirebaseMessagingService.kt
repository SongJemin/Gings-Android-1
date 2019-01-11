package com.computer.inu.myworkinggings.Moohyeon.Firebase

import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.computer.inu.myworkinggings.Moohyeon.Activity.LoginActivity
import com.computer.inu.myworkinggings.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "MessagingService"

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        if (isAppRunning(this)) {           // 앱 포그라운드 실행중
               val intent = Intent(this,LoginActivity::class.java)          // 로그인 화면으로 이동.
            val sender_id = remoteMessage!!.data?.toString()
            intent.putExtra("sender_id",sender_id)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
           startActivity(intent)

        } else {              // 앱 백그라운드 실행중
            Log.d(TAG, "app background running...")
            val intent = Intent(this,LoginActivity::class.java)          // 로그인 화면으로 이동.
            val sender_id = remoteMessage!!.data?.toString()
            intent.putExtra("sender_id",sender_id)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


        val notification = remoteMessage!!.notification
         val sender_id = remoteMessage!!.data?.toString()

        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("sender_id",sender_id)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
                .setSmallIcon(R.color.material_blue_grey_800)
                //                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.color_push))
                .setContentTitle(notification?.title.toString())
                .setContentText(notification?.body.toString())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVibrate(longArrayOf(300, 500, 300, 500))     //진동
                .setContentIntent(pendingIntent)


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val channelId = "GingsAlarm"
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
