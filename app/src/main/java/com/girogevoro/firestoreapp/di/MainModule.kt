package com.girogevoro.firestoreapp.di

import com.girogevoro.firestoreapp.data.repository.FakeNotesRepo
import com.girogevoro.firestoreapp.domain.NotesInteractor
import com.girogevoro.firestoreapp.domain.NotesRepo
import com.girogevoro.firestoreapp.ui.MainActivity
import com.girogevoro.firestoreapp.ui.NoteDialogFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module
class MainModule {

    @Provides
    @Singleton
    fun notesRepo(): NotesRepo = FakeNotesRepo()


    @Provides
    @Singleton
    fun notesInteractor(repo: NotesRepo): NotesInteractor = NotesInteractor(repo)

}

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

}

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun bindNoteDialogFragment(): NoteDialogFragment
}