package com.tony_tang.android.demo.feature.note_detail;


import android.app.Fragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.FragmentKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {NoteDetailFragmentComponent.class})
public abstract class NoteDetailFragmentProvider {

    @Binds
    @IntoMap
    @FragmentKey(NoteDetailFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> provideNoteDetailFragmentFactory(NoteDetailFragmentComponent.Builder builder);
}
