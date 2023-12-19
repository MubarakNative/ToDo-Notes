package com.devplatform.mubarak.data.reminder

interface AlarmScheduler {

    fun schedule(alarmItem: AlarmItem)
    fun cancel(alarmItem: AlarmItem)
}