package com.tony_tang.android.demo.feature.note_detail;

import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {NoteDetailFragmentComponent.class})
public abstract class NoteDetailFragmentProvider {

    @Binds
    @IntoMap
    @FragmentKey(NoteDetailFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> provideNoteDetailFragmentFactory(NoteDetailFragmentComponent.Builder builder);
}
