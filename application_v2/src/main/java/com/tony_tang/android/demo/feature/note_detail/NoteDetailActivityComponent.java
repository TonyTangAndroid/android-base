package com.tony_tang.android.demo.feature.note_detail;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent(modules = {NoteDetailFragmentProvider.class})
public interface NoteDetailActivityComponent extends AndroidInjector<NoteDetailActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<NoteDetailActivity> {
    }
}
