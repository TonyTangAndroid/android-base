package com.tony_tang.android.demo.common;

import com.tony_tang.android.demo.feature.note_detail.NoteDetailFragment;
import com.tony_tang.android.demo.feature.note_detail.NoteDetailModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DemoFragmentInjector {

    @ContributesAndroidInjector(modules = {NoteDetailModule.class})
    abstract NoteDetailFragment bindNoteDetailFragment();
}
