package com.devplatform.mubarak.domain.repository

import com.devplatform.mubarak.data.local.database.NoteDatabase
import com.devplatform.mubarak.data.local.model.Note
import com.devplatform.mubarak.data.local.model.ReminderNoteItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(private val noteDatabase: NoteDatabase): NoteRepository {


    override suspend fun upsertNotes(note: Note) {
        noteDatabase.getDao.upsertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDatabase.getDao.deleteNote(note)
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDatabase.getDao.getNotes()
    }


    override suspend fun deleteAllNotes() {
        noteDatabase.getDao.deleteAllNotes()
    }

    override fun searchNote(searchQuery: String) =
        noteDatabase.getDao.searchNote(searchQuery)



}