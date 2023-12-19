package com.devplatform.mubarak.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devplatform.mubarak.data.local.model.Note
import com.devplatform.mubarak.data.local.model.ReminderNoteItem
import com.devplatform.mubarak.domain.repository.NoteRepository
import com.devplatform.mubarak.domain.repository.ReminderNoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val notesRepository: NoteRepository,
    private val reminderNoteRepository: ReminderNoteRepository
) : ViewModel() {


    fun getNotes(): Flow<List<Note>> {
        return notesRepository.getAllNotes()
    }

    fun upsertNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.upsertNotes(note)
        }
    }



    fun deleteNote(note: Note) {
        viewModelScope.launch {
            Dispatchers.IO
            notesRepository.deleteNote(note)
        }

    }

    fun deleteAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.deleteAllNotes()
        }
    }

    fun searchNote(searchQuery: String) =
        notesRepository.searchNote(searchQuery)


    // reminder note
    fun insertReminderNote(reminderNoteItem: ReminderNoteItem) {
        viewModelScope.launch {
            reminderNoteRepository.upsertReminderNotes(reminderNoteItem)
        }
    }

    fun updateReminderNote(reminderNoteItem: ReminderNoteItem){
        viewModelScope.launch {
            reminderNoteRepository.upsertReminderNotes(reminderNoteItem)
        }
    }

    fun deleteReminderNote(reminderNoteItem: ReminderNoteItem){
        viewModelScope.launch {
            reminderNoteRepository.deleteNote(reminderNoteItem)
        }
    }

    fun deleteAllReminderNote(){
        viewModelScope.launch {
            reminderNoteRepository.deleteAllRemindersNotes()
        }
    }

    fun getAllReminderNote()= reminderNoteRepository.getAllNotes()

}