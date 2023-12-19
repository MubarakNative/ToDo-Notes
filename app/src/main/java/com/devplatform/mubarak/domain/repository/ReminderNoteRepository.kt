package com.devplatform.mubarak.domain.repository

import com.devplatform.mubarak.data.local.model.Note
import com.devplatform.mubarak.data.local.model.ReminderNoteItem
import kotlinx.coroutines.flow.Flow

interface ReminderNoteRepository {

    suspend fun upsertReminderNotes(note: ReminderNoteItem)

    suspend fun deleteNote(note: ReminderNoteItem)

    fun getAllNotes(): Flow<List<ReminderNoteItem>>

    suspend fun deleteAllRemindersNotes()

    fun searchNote(searchQuery: String): Flow<List<ReminderNoteItem>>
}