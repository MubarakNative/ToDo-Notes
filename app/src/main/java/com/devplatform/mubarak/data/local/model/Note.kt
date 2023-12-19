package com.devplatform.mubarak.data.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "note")
@Parcelize
data class Note(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String,
    var description: String
): Parcelable


