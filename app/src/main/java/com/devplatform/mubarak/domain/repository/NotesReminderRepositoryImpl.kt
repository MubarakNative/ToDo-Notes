package com.devplatform.mubarak.domain.repository

import com.devplatform.mubarak.data.local.database.NoteDatabase
import com.devplatform.mubarak.data.local.model.Note
import com.devplatform.mubarak.data.local.model.ReminderNoteItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesReminderRepositoryImpl @Inject constructor(private val noteDatabase: NoteDatabase): ReminderNoteRepository {
    override suspend fun upsertReminderNotes(note: ReminderNoteItem) {
        noteDatabase.getReminderDao.upsertNote(note)
    }

    override suspend fun deleteNote(note: ReminderNoteItem) {
        noteDatabase.getReminderDao.deleteNote(note)
    }

    override fun getAllNotes(): Flow<List<ReminderNoteItem>> {
        return noteDatabase.getReminderDao.getNotes()
    }

    override suspend fun deleteAllRemindersNotes() {
        noteDatabase.getReminderDao.deleteAllNotes()
    }

    override fun searchNote(searchQuery: String): Flow<List<ReminderNoteItem>> {
       return noteDatabase.getReminderDao.searchNote(searchQuery)
    }


}