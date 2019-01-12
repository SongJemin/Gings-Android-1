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
import android.R.attr.data
import com.computer.inu.myworkinggings.R.string.default_notification_channel_id
import org.jetbrains.anko.ctx
import org.jetbrains.anko.notificationManager

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "MessagingService"

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel("gings", name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
        val notification = remoteMessage!!.notification
        val data = remoteMessage!!.data

        val title = remoteMessage.getData().get("title")!!
        val body = remoteMessage.getData().get("body")!!
        var clickAction = "오류"
         clickAction = remoteMessage.getData().get("clickAction")!!
        val sender_id= remoteMessage.getData().get("sender_id")!!
/*
        sendNotification(title,body)*/

       //intent.putExtra("sender_id", sender_id)
        if (isAppRunning(this)) {           // 앱 포그라운드 실행중
            Log.d(TAG, "앱 포그라운드")
      /*      val intent = Intent(this,LoginActivity::class.java)          // 로그인 화면으로 이동.
            intent.putExtra("sender_id",sender_id)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)*/

            Log.d(TAG, "앱 sendNotification")
            val channelId = "default_channel_id"
            val channelDescription = "Default Channel"
// Since android Oreo notification channel is needed.
//Check if notification channel exists and if not create one

            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("sender_id",sender_id)
            intent.putExtra("clickAction",clickAction)
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT)

            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val nBuilder = NotificationCompat.Builder(this,channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setChannelId(channelId)
                    .setVibrate(longArrayOf(300, 500, 300, 500))     //진동
                    .setLights(Color.BLUE, 1, 1)
                    .setContentIntent(pendingIntent)

            val nManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.notify(0 /* ID of notification */, nBuilder.build())

        } else {              // 앱 백그라운드 실행중
            Log.d(TAG, "앱 백그라운드")
            Log.d(TAG, "앱 sendNotification")
            val channelId = "default_channel_id"
            val channelDescription = "Default Channel"
// Since android Oreo notification channel is needed.
//Check if notification channel exists and if not create one

            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("sender_id",sender_id)
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT)

            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val nBuilder = NotificationCompat.Builder(this,channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setChannelId(channelId)
                    .setVibrate(longArrayOf(300, 500, 300, 500))     //진동
                    .setLights(Color.BLUE, 1, 1)
                    .setContentIntent(pendingIntent)

            val nManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.notify(0 /* ID of notification */, nBuilder.build())
        }
    }
        /*private fun sendNotification(title: String, body: String) {
            Log.d(TAG, "앱 sendNotification")
            val channelId = "default_channel_id"
            val channelDescription = "Default Channel"
// Since android Oreo notification channel is needed.
//Check if notification channel exists and if not create one

            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("sender_id",sender_id)
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT)

            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val nBuilder = NotificationCompat.Builder(this,channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setChannelId(channelId)
                    .setVibrate(longArrayOf(300, 500, 300, 500))     //진동
                    .setLights(Color.BLUE, 1, 1)
                    .setContentIntent(pendingIntent)

            val nManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.notify(0 *//* ID of notification *//*, nBuilder.build())
        }
*/


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