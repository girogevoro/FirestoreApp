package com.girogevoro.firestoreapp.domain.entity

import com.girogevoro.firestoreapp.domain.NotesRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class FakeNotesRepo : NotesRepo {
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

    override fun getAllNotes(): Flow<List<Note>> {
        return flowOf(notes)
    }

    override fun saveNote(note: Note) {
        notes.add(note)
    }

    override fun deleteNote(note: Note) {
        notes.remove(note)
    }

}