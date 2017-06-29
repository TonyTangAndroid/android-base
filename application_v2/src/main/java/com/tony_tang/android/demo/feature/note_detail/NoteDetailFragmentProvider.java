package com.tony_tang.android.demo.feature.note_detail;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class NoteDetailFragmentProvider {

    @ContributesAndroidInjector(modules = {NoteDetailModule.class})
    abstract NoteDetailFragment provideNoteDetailFragmentFactory();
}
