package com.minseok.fcmexample

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Created by minseok on 19/12/2018.
 * FCMExample.
 */
class FirebaseMessagingService : FirebaseMessagingService() {
    val TAG = "fcm-service"

    // 메시지를 받을때 호출
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.i(TAG, "onMessageReceived")

        remoteMessage?.let { message ->
            Log.d(TAG, "Message Notification Title: " + message.notification?.title)
            Log.d(TAG, "Message Notification Body: " + message.notification?.body)

            val title = message.notification?.title ?:  "unknown"
            val content = message.notification?.body ?: "unknown"

            sendNotification(title, content)
        }
    }

    // Notification 생성
    private fun sendNotification(title: String, message: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder(this, "__chennel__id__")
        } else {
            NotificationCompat.Builder(this)
        }.apply {
            setLargeIcon(BitmapFactory.decodeResource(resources, android.R.drawable.ic_menu_view))
            setSmallIcon(R.mipmap.ic_launcher)
            setContentTitle(title)
            setContentText(message)
            setAutoCancel(true)
            setContentIntent(pendingIntent)
        }.also {
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(0, it.build())
        }
    }
}