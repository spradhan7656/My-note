package com.example.mynotemvvm

import android.app.Application
import com.example.mynotemvvm.database.NoteDatabase
import com.example.mynotemvvm.database.NotesRepository

class NoteApplication:Application() {
    lateinit var notesRepository: NotesRepository
    override fun onCreate() {
        super.onCreate()
        initilize()
    }

    private fun initilize() {
        val database = NoteDatabase.getDatabase(applicationContext)
        notesRepository=NotesRepository(database)
    }
}