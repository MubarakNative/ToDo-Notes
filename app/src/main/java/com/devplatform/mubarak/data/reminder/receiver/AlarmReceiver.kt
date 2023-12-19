package com.devplatform.mubarak.data.reminder.receiver

import android.content.BroadcastReceiver
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build.VERSION.SDK_INT
import android.os.Parcelable
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.devplatform.mubarak.data.reminder.AlarmItem
import com.devplatform.mubarak.data.reminder.AlarmSchedulerImpl
import com.devplatform.mubarak.notes.R
import com.devplatform.mubarak.utils.constant.AppConstants

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val demoDa = intent?.parcelable<AlarmItem>("demoDa")

        demoDa?.let {
            createNotification(context!!,it.alarmTitle,it.alarmDescription)

            val alarmSchedulerImpl = AlarmSchedulerImpl(context)
            alarmSchedulerImpl.cancel(it)
        }


    }

    @Suppress("MissingPermission")
    private fun createNotification(context: Context,title:String,description: String){



        val notification = NotificationCompat.Builder(context, AppConstants.NOTIFICATION_CHANNEL_ID).apply {
            setSmallIcon(R.drawable.remainder_icon24px)
            setContentTitle(title)
            setContentText(description)
            setOngoing(false)
            color = Color.YELLOW

        }


        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1,notification.build())
    }

    private inline fun < reified T : Parcelable> Intent.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }
}