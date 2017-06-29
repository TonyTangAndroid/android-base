package com.tony_tang.android.demo.common;

import com.tony_tang.android.demo.feature.note_detail.NoteDetailActivity;
import com.tony_tang.android.demo.feature.note_detail.NoteDetailFragmentProvider;
import com.tony_tang.android.demo.feature.note_detail.NoteDetailModule;
import com.tony_tang.android.demo.feature.note_list.NoteListActivity;
import com.tony_tang.android.demo.feature.note_list.NoteListActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DemoActivityInjector {

    @ContributesAndroidInjector(modules = NoteListActivityModule.class)
    abstract NoteListActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {NoteDetailModule.class, NoteDetailFragmentProvider.class})
    abstract NoteDetailActivity bindDetailActivity();
}
