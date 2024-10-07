package com.example.mynotemvvm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mynotemvvm.adapters.NotesAdapter
import com.example.mynotemvvm.database.NoteDatabase
import com.example.mynotemvvm.databinding.ActivityMainBinding
import com.example.mynotemvvm.models.Note
import com.example.mynotemvvm.models.NoteViewModel
import com.example.mynotemvvm.models.NoteViewModelFactory

class MainActivity : AppCompatActivity(),NotesAdapter.NoteitemClickListener,PopupMenu.OnMenuItemClickListener {
    private lateinit var  binder:ActivityMainBinding
    private lateinit var database:NoteDatabase
    lateinit var viewModel: NoteViewModel
    lateinit var adapter: NotesAdapter
    lateinit var selectedNote: Note

    private val updateNote=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        if(result.resultCode==Activity.RESULT_OK){
            val note=result.data?.getSerializableExtra("note") as? Note
            if(note!=null){
                viewModel.updateNote(note)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binder.root)
        enableEdgeToEdge()
        // This is for initialization of ui
        InitUI()
        val repository=(application as NoteApplication).notesRepository
        viewModel=ViewModelProvider(this,NoteViewModelFactory(repository)).get(NoteViewModel::class.java)
        viewModel.allnotes.observe(this){list->
            list?.let {
                adapter.updateList(list)
            }

        }
    }

    private fun InitUI() {
        binder.recyclerView.setHasFixedSize(true)
        binder.recyclerView.layoutManager=StaggeredGridLayoutManager(2,LinearLayout.VERTICAL)
        adapter= NotesAdapter(this,this)
        binder.recyclerView.adapter=adapter

        val getContent=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
            if(result.resultCode == Activity.RESULT_OK){
                val note=result.data?.getSerializableExtra("note") as? Note
                if(note !=null){
                    viewModel.insertNote(note)
                }

            }
        }
        binder.fbAddNote.setOnClickListener {
            val intent = Intent(this,AddNote::class.java)
            getContent.launch(intent)
        }
        binder.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!=null){
                    adapter.filterList(newText)
                }
                return true
            }

        })
    }

    override fun onItemClicked(note: Note) {
        val intent=Intent(this@MainActivity,AddNote::class.java)
        intent.putExtra("currnent_note",note)
        updateNote.launch(intent)
    }

    override fun onLongItemClicked(note: Note, cardView: CardView) {
        selectedNote=note
        popDisplay(cardView)
    }

    private fun popDisplay(cardView: CardView) {
        val popup = PopupMenu(this,cardView)
        popup.setOnMenuItemClickListener(this@MainActivity)
        popup.inflate(R.menu.pop_menu)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.delete_note){
            viewModel.deleteNote(selectedNote)
            return true
        }
        return false
    }
}