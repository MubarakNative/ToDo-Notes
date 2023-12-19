package com.devplatform.mubarak.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devplatform.mubarak.data.local.model.Note
import com.devplatform.mubarak.data.local.model.ReminderNoteItem
import com.devplatform.mubarak.data.local.model.NoteDao
import com.devplatform.mubarak.data.local.model.ReminderNoteDao

@Database(entities = [Note::class,ReminderNoteItem::class],
    version = 1,
    exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract val getDao: NoteDao
    abstract val getReminderDao: ReminderNoteDao
}