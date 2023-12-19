package com.devplatform.mubarak.data.di.db

import android.content.Context
import androidx.room.Room
import com.devplatform.mubarak.data.local.database.NoteDatabase
import com.devplatform.mubarak.domain.repository.NoteRepository
import com.devplatform.mubarak.domain.repository.NotesReminderRepositoryImpl
import com.devplatform.mubarak.domain.repository.NotesRepositoryImpl
import com.devplatform.mubarak.domain.repository.ReminderNoteRepository
import com.devplatform.mubarak.utils.constant.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context = context,
            NoteDatabase::class.java,
            AppConstants.DBNAME
        ).build()
    }

    @Singleton
    @Provides
    fun noteRepoImpl(noteDatabase: NoteDatabase):NoteRepository{
        return NotesRepositoryImpl(noteDatabase)
    }

    @Singleton
    @Provides
    fun reminderRepoImpl(noteDatabase: NoteDatabase):ReminderNoteRepository{
        return NotesReminderRepositoryImpl(noteDatabase)
    }
}