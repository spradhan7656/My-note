package com.example.mynotemvvm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mynotemvvm.databinding.ActivityAddNoteBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.logging.SimpleFormatter

class AddNote : AppCompatActivity() {
    private lateinit var bindign:ActivityAddNoteBinding
    private lateinit var note:com.example.mynotemvvm.models.Note
    private  lateinit var oldNote:com.example.mynotemvvm.models.Note
    var isUpdate=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindign=ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(bindign.root)
        enableEdgeToEdge()
        try {
            oldNote=intent.getSerializableExtra("currnent_note") as com.example.mynotemvvm.models.Note
            bindign.etTitle.setText(oldNote.title)
            bindign.etNote.setText(oldNote.note)
            isUpdate=true

        }catch (e :Exception){
            e.printStackTrace()
        }
        bindign.imgCheck.setOnClickListener {
            val title=bindign.etTitle.text.toString()
            val note_desc=bindign.etNote.text.toString()

            if(title.isNotEmpty() || note_desc.isNotEmpty()){
                val formatter=SimpleDateFormat("EEE,d MM yyyy HH:mm a")
                if(isUpdate){
                    note = com.example.mynotemvvm.models.Note(
                        oldNote.id, title, note_desc, formatter.format(Date())
                    )
                }else{
                    note=com.example.mynotemvvm.models.Note(
                        null, title, note_desc, formatter.format(Date())
                    )
                }
                val intent=Intent()
                intent.putExtra("note", note)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            else{
               Toast.makeText(this@AddNote,"Please enter some data ",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
        }
        bindign.imgBackArrow.setOnClickListener {
            onBackPressed()
        }

    }
}