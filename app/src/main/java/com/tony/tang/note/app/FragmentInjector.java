package com.tony.tang.note.app;

import com.tony.tang.note.ui.feature.note.creation.NoteCreateFragment;
import com.tony.tang.note.ui.feature.note.creation.NoteCreateModule;
import com.tony.tang.note.ui.feature.note.detail.NoteDetailFragment;
import com.tony.tang.note.ui.feature.note.detail.NoteDetailModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentInjector {
    @FragmentScope
    @ContributesAndroidInjector(modules = {NoteDetailModule.class})
    abstract NoteDetailFragment bindNoteDetailFragment();

    @FragmentScope
    @ContributesAndroidInjector(modules = {NoteCreateModule.class})
    abstract NoteCreateFragment bindNoteCreateFragment();

}
