package com.devplatform.mubarak.data.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Entity(tableName = "reminder_note")
@Parcelize
data class ReminderNoteItem(


    var reminderTitle: String,
    var reminderDescription: String,
    val cpTime:String
): Parcelable{
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}


