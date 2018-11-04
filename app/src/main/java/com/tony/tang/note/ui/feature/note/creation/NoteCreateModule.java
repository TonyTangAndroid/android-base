package com.tony.tang.note.ui.feature.note.creation;

import com.tony.tang.note.presenter.NoteCreatePresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class NoteCreateModule {

    @Provides
    NoteCreatePresenter.NoteCreateView provideNoteCreateView(NoteCreateFragment noteCreateFragment) {
        return noteCreateFragment;
    }

}
