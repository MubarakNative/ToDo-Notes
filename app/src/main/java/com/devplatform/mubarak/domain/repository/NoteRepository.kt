package com.devplatform.mubarak.domain.repository

import com.devplatform.mubarak.data.local.model.Note
import com.devplatform.mubarak.data.local.model.ReminderNoteItem
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun upsertNotes(note: Note)

    suspend fun deleteNote(note: Note)

    fun getAllNotes(): Flow<List<Note>>

    suspend fun deleteAllNotes()

    fun searchNote(searchQuery: String): Flow<List<Note>>
}