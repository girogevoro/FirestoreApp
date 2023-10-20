package com.girogevoro.firestoreapp.domain

import com.girogevoro.firestoreapp.domain.entity.Note
import kotlinx.coroutines.flow.SharedFlow

interface NotesRepo {
    fun getAllNotes(): SharedFlow<List<Note>>
    fun saveNote(note: Note)
    fun deleteNote(note: Note)
}