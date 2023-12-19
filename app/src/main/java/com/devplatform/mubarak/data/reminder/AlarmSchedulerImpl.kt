package com.devplatform.mubarak.data.reminder

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.content.Context
import android.os.Build
import android.os.Parcelable
import android.os.Build.VERSION.SDK_INT
import android.app.PendingIntent
import android.content.Intent
import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import com.devplatform.mubarak.data.reminder.receiver.AlarmReceiver
import java.util.PriorityQueue

class AlarmSchedulerImpl(private val context: Context) : AlarmScheduler {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
    private val alarmQueue = PriorityQueue<AlarmItem>(compareBy { it.timeInMillis })

    @SuppressLint("ScheduleExactAlarm")
    override fun schedule(alarmItem: AlarmItem) {
        alarmQueue.offer(alarmItem)
        scheduleNextAlarm()
    }

    override fun cancel(alarmItem: AlarmItem) {
        alarmQueue.remove(alarmItem)
        scheduleNextAlarm()
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun scheduleNextAlarm() {
        val nextAlarm = alarmQueue.peek()
        if (nextAlarm != null) {
            val alarmReceiverIntent = Intent(context, AlarmReceiver::class.java).apply {
                putExtra("demoData", nextAlarm)
            }

            val requestCode = /*nextAlarm.id.toInt()*/12

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                alarmReceiverIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager?.let {
                    it.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        nextAlarm.timeInMillis,
                        pendingIntent
                    )

            }
        }
    }
}
