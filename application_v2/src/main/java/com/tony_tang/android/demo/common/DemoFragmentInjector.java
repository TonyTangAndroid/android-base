package com.tony_tang.android.demo.common;

import com.tony_tang.android.demo.feature.note_creation.NoteCreateFragment;
import com.tony_tang.android.demo.feature.note_creation.NoteCreateModule;
import com.tony_tang.android.demo.feature.note_detail.NoteDetailFragment;
import com.tony_tang.android.demo.feature.note_detail.NoteDetailModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DemoFragmentInjector {

    @ContributesAndroidInjector(modules = {NoteDetailModule.class})
    abstract NoteDetailFragment bindNoteDetailFragment();


    @ContributesAndroidInjector(modules = {NoteCreateModule.class})
    abstract NoteCreateFragment bindNoteCreateFragment();

}
