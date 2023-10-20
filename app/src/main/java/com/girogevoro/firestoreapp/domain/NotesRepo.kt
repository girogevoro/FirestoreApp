package com.girogevoro.firestoreapp.domain

import com.girogevoro.firestoreapp.domain.entity.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepo {
    fun getAllNotes(): Flow<List<Note>>
    fun saveNote(note: Note)
    fun deleteNote(note: Note)
}