package com.devplatform.mubarak.data.reminder

import android.app.PendingIntent
import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class AlarmItem(
    val timeInMillis: Long,
    val alarmTitle: String,
    val alarmDescription: String,
    var pendingIntent: PendingIntent?=null
):Parcelable
