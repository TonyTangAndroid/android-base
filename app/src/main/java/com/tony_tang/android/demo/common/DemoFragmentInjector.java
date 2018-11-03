package com.tony_tang.android.demo.common;

import com.tony_tang.android.demo.common.scope.FragmentScope;
import com.tony_tang.android.demo.feature.note_creation.NoteCreateFragment;
import com.tony_tang.android.demo.feature.note_creation.NoteCreateModule;
import com.tony_tang.android.demo.feature.note_detail.NoteDetailFragment;
import com.tony_tang.android.demo.feature.note_detail.NoteDetailModule;
import com.tony_tang.android.demo.feature.note_list.fragment.NoteListFragment;
import com.tony_tang.android.demo.feature.note_list.fragment.NoteListFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DemoFragmentInjector {
    @FragmentScope
    @ContributesAndroidInjector(modules = {NoteDetailModule.class})
    abstract NoteDetailFragment bindNoteDetailFragment();

    @FragmentScope
    @ContributesAndroidInjector(modules = {NoteListFragmentModule.class})
    abstract NoteListFragment bindNoteListFragmentModule();

    @FragmentScope
    @ContributesAndroidInjector(modules = {NoteCreateModule.class})
    abstract NoteCreateFragment bindNoteCreateFragment();

}
