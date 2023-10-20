package com.girogevoro.firestoreapp.data.repository

import com.girogevoro.firestoreapp.domain.NotesRepo
import com.girogevoro.firestoreapp.domain.entity.Note
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class FakeNotesRepo : NotesRepo {
    private val sharedNotes = MutableSharedFlow<List<Note>>(1)
    private val notes = mutableListOf<Note>(
        Note(
            dateTime = LocalDateTime.of(LocalDate.of(2023, 9, 1), LocalTime.of(8, 15)),
            pressure1 = 122, pressure2 = 63, pulse = 70
        ),
        Note(
            dateTime = LocalDateTime.of(LocalDate.of(2023, 10, 2), LocalTime.of(8, 20)),
            pressure1 = 129, pressure2 = 65, pulse = 57
        ),
        Note(
            dateTime = LocalDateTime.of(LocalDate.of(2023, 11, 3), LocalTime.of(9, 30)),
            pressure1 = 137, pressure2 = 68, pulse = 69
        ),
        Note(
            dateTime = LocalDateTime.of(LocalDate.of(2023, 12, 3), LocalTime.of(7, 15)),
            pressure1 = 127, pressure2 = 63, pulse = 57
        )
    )

    init {
        sharedNotes.tryEmit(notes)
    }

    override fun getAllNotes(): SharedFlow<List<Note>> {
        return sharedNotes
    }

    override fun saveNote(note: Note) {
        notes.add(note)
        sharedNotes.tryEmit(notes)
    }

    override fun deleteNote(note: Note) {
        notes.remove(note)
        sharedNotes.tryEmit(notes)
    }

}