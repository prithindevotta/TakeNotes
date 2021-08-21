package com.android.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import android.widget.ViewSwitcher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), onClick {

    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = CustomAdapter(this, this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer {List ->
            List ?.let {
                adapter.update(it)
            }
        })

    }

    override fun onItemClick(note: Note) {
        viewModel.deleteNotes(note)
        Toast.makeText(this, "${note.text} deleted", Toast.LENGTH_LONG).show()
    }

    fun insert(view: View) {
        val noteText = textView.text.toString()
        if (noteText.isNotEmpty()){
            viewModel.insertNotes(Note(noteText))
            textView.text.clear()
            Toast.makeText(this, "$noteText inserted", Toast.LENGTH_LONG).show()
        }
    }
}