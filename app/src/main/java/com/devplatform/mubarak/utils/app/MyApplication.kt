package com.devplatform.mubarak.utils.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.view.Window
import android.view.WindowManager
import androidx.core.view.WindowCompat
import com.devplatform.mubarak.utils.constant.AppConstants
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication: Application(){

    override fun onCreate() {
        super.onCreate()



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                AppConstants.NOTIFICATION_CHANNEL_ID,
                "Alarm's and reminder",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "This category is for send alarms and Reminders"
            }

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}