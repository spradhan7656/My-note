package com.example.mynotemvvm.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mynotemvvm.models.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    /**
     * (onConflict = OnConflictStrategy.REPLACE) this is used for if the note is already existing then replace it with a new note
     */

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes_table order by id ASC")
    fun getAllnotes():LiveData<List<Note>>

    @Query("UPDATE notes_table Set note=:note ,title=:title WHERE id=:id ")
    suspend fun update(id:Int?,title:String?,note:String?)
    /**
     * Query("UPDATE notes_table Set note=:note ,title=:title WHERE id=:id ") use not use because the i want to update the notes accroding to the id
     */
}