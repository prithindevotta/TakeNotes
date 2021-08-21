package com.android.notes

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {
    var repository: NoteRepository
    var allNotes : LiveData<List<Note>>

    init {
        val dao = NoteDatabase.getDatabase(application).getNoteDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }
    fun deleteNotes(note: Note) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(note)
    }
    fun insertNotes(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

}