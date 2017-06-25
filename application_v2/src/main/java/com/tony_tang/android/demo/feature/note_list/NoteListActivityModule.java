package com.tony_tang.android.demo.feature.note_list;

import com.jordifierro.androidbase.domain.interactor.note.GetNotesUseCase;

import dagger.Module;
import dagger.Provides;

/**

 */
@Module
public class NoteListActivityModule {

    @Provides
    NoteListView provideMainView(NoteListActivity mainActivity) {
        return mainActivity;
    }

    @Provides
    NoteListPresenter provideMainPresenter(NoteListView noteListView, GetNotesUseCase getNotesUseCase) {
        return new NoteListPresenterImpl(noteListView, getNotesUseCase);
    }
}
