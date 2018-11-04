package com.tony.tang.note.ui.feature.note.detail;

import com.tony.tang.note.presenter.NoteDetailPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class NoteDetailModule {

    @Provides
    NoteDetailPresenter.NoteDetailView provideDetailFragmentView(NoteDetailFragment noteDetailFragment) {
        return noteDetailFragment;
    }

}
