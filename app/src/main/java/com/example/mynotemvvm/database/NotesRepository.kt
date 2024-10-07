package com.example.mynotemvvm.database

import androidx.lifecycle.LiveData
import com.example.mynotemvvm.models.Note

class NotesRepository(private val noteDao: NoteDatabase) {

    val allNotes: LiveData<List<Note>> = noteDao.getNoteDao().getAllnotes()
    suspend fun insert(note: Note){
        noteDao.getNoteDao().insert(note)
    }
    suspend fun delete( note: Note){
        noteDao.getNoteDao().delete(note)
    }
    suspend fun update(note: Note){
        noteDao.getNoteDao().update(note.id,note.title,note.note)
    }
}