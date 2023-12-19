package com.devplatform.mubarak.data.local.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Upsert
    suspend fun upsertNote(note: Note)  // insert a single (specific) note

    @Delete
    suspend fun deleteNote(note: Note) // delete a specific note

    @Query("SELECT * FROM note ORDER BY id DESC")
    fun getNotes(): Flow<List<Note>>    // return all the note

    @Query("DELETE FROM note")
    suspend fun deleteAllNotes()    // delete whole data from table

    @Query("SELECT * FROM note WHERE title LIKE '%' || :searchQuery || '%' OR description LIKE '%' || :searchQuery || '%'")   // searching a note which they match with title and description
    fun searchNote(searchQuery:String): Flow<List<Note>>


}