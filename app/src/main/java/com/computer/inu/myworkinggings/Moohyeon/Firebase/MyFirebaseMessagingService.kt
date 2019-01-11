package com.computer.inu.myworkinggings.Moohyeon.Firebase

import android.app.*
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
import android.system.Os.link
import android.os.Bundle
import com.computer.inu.myworkinggings.Jemin.Activity.MainActivity
import org.jetbrains.anko.notificationManager


class MyFirebaseMessagingService : FirebaseMessagingService() {


    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]

    // 푸시 메세지를 수신했을때 호출되는 메소드
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        Log.d(TAG, "메세지를 받음")
        if (isAppRunning(this)) {           // 앱 포그라운드 실행중
            Log.d(TAG, "앱 포그라운드 실행중")

            // 푸시 메세지 내용 pushDataMap
            val sender_id = remoteMessage!!.getData().get("sender_id").toString()
            // 이렇게 데이터에 있는걸 키값으로 뽑아 쓰면 된다.
            val title = remoteMessage.getData().get("title")!!
            val body = remoteMessage.notification!!.body!!
            //val body = "asdf"
            //val body = remoteMessage.getData()!!.get("body")!!
            sendNotification(title, body ,sender_id)


        }
        // 앱 백그라운드 실행중
        else {
            Log.d(TAG, "app background running...")
            // 푸시 메세지 내용 pushDataMap
            val sender_id = remoteMessage!!.getData().get("sender_id").toString()
            val title = remoteMessage.getData().get("title")!!
            val body = remoteMessage.getData()!!.get("body")!!
            Log.v("TAG", "백그라운드 타이틀 = " + title)
            Log.v("TAG", "백그라운드 바디 = " + body)
            sendNotification(title, body,sender_id)
        }

    }

    // 푸시 메세지를 알림으로 표현해주는 메소드
    private fun sendNotification(title : String, body : String, sender_id :String) {
        val channelId = getString(R.string.default_notification_channel_id)
        val channelName ="Channel Name"
        val intent = Intent(this, MainActivity::class.java)
        Log.v("TAG", "sendNotification = " + body)

        val pendingIntent : PendingIntent = PendingIntent.getActivity(this,0,intent,
                PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val nBuilder = Notification.Builder(this,channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setChannelId(channelId)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVibrate(longArrayOf(300, 500, 300, 500))     //진동
                .setLights(Color.BLUE,1,1)
                .setContentIntent(pendingIntent)

        var nManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =  NotificationChannel(channelId, title, NotificationManager.IMPORTANCE_DEFAULT)
            channel.setDescription(body)
           nManager.createNotificationChannel(channel);
            nBuilder.setChannelId(channelId)
        }
        nManager.notify(0 /* ID of notification */, nBuilder.build())
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