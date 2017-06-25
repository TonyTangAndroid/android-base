package com.tony_tang.android.demo.feature.note_list;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**

 */
@Subcomponent(modules = NoteListActivityModule.class)
public interface NoteListActivityComponent extends AndroidInjector<NoteListActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<NoteListActivity> {
    }
}
