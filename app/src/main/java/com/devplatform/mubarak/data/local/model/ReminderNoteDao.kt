package com.devplatform.mubarak.data.local.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderNoteDao {

    @Upsert
    suspend fun upsertNote(note: ReminderNoteItem)  // insert a single (specific) note

    @Delete
    suspend fun deleteNote(note: ReminderNoteItem) // delete a specific note

    @Query("SELECT * FROM reminder_note ORDER BY id DESC")
    fun getNotes(): Flow<List<ReminderNoteItem>>    // return all the note

    @Query("DELETE FROM reminder_note")
    suspend fun deleteAllNotes()    // delete whole data from table

    @Query("SELECT * FROM reminder_note WHERE reminderTitle LIKE '%' || :searchQuery || '%' OR reminderDescription LIKE '%' || :searchQuery || '%'")   // searching a note which they match with title and description
    fun searchNote(searchQuery:String): Flow<List<ReminderNoteItem>>


}