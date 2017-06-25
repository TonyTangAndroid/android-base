package com.tony_tang.android.demo.feature.note_detail;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent(modules = NoteDetailModule.class)
public interface NoteDetailFragmentComponent extends AndroidInjector<NoteDetailFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<NoteDetailFragment> {
    }
}
