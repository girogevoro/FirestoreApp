package com.girogevoro.firestoreapp.data.repository

import android.util.Log
import com.girogevoro.firestoreapp.FIRESTORE_DB_NAME
import com.girogevoro.firestoreapp.LOG_TAG
import com.girogevoro.firestoreapp.NO_ID
import com.girogevoro.firestoreapp.data.repository.mappers.toFirestoreEntity
import com.girogevoro.firestoreapp.data.repository.mappers.toNote
import com.girogevoro.firestoreapp.domain.NotesRepo
import com.girogevoro.firestoreapp.domain.entity.Note
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class FirestoreNotesRepo:NotesRepo {
    private val sharedFlow = MutableSharedFlow<List<Note>>(1)
    private val notes = mutableListOf<Note>()

    private val db = Firebase.firestore.collection(FIRESTORE_DB_NAME)

    init {
        db.get()
            .addOnFailureListener { e ->
                Log.d(LOG_TAG, e.message.toString())
            }
        observeDbChanges()
    }

    private fun observeDbChanges() {
        db.addSnapshotListener { value, error ->
            value?.let {
                it.documentChanges.forEach { documentChange ->
                    val docData = documentChange.document.data
                    val docId = documentChange.document.id

                    when (documentChange.type) {

                        DocumentChange.Type.ADDED -> {
                            notes.add(docData.toNote(docId))
                        }

                        DocumentChange.Type.REMOVED -> {
                            val dataToRemove = notes.findLast { note ->
                                note.id == docId
                            }
                            notes.remove(dataToRemove)
                        }

                        DocumentChange.Type.MODIFIED -> {
                            val dataToRemove = notes.findLast { note ->
                                note.id == docId
                            }
                            notes.remove(dataToRemove)
                            notes.add(docData.toNote(docId))
                        }

                        else -> {
                        }
                    }
                }
            }
            sharedFlow.tryEmit(notes)
        }
    }

    override fun getAllNotes(): SharedFlow<List<Note>> = sharedFlow

    override fun saveNote(note: Note) {
        if (note.id == NO_ID) {
            db.add(note.toFirestoreEntity())
        } else {
            db.document(note.id)
                .set(note.toFirestoreEntity())
        }
            .addOnFailureListener { e ->
                Log.d(LOG_TAG, e.message.toString())
            }
    }

    override fun deleteNote(note: Note) {
        db.document(note.id).delete()
    }
}