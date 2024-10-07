package com.example.mynotemvvm.models

import android.app.Application
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotemvvm.database.NoteDatabase
import com.example.mynotemvvm.database.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NotesRepository):ViewModel() {

    val allnotes: LiveData<List<Note>>
    init {
        allnotes=repository.allNotes
    }

    fun deleteNote(note: Note)=viewModelScope.launch ( Dispatchers.IO ){
        repository.delete(note)
    }

    fun insertNote(note: Note)=viewModelScope.launch ( Dispatchers.IO ){
        repository.insert(note)
    }
    fun updateNote(note: Note)=viewModelScope.launch ( Dispatchers.IO ){
        repository.update(note)
    }

}