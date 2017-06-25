package com.tony_tang.android.demo.feature.note_list;

import com.jordifierro.androidbase.domain.interactor.note.GetNotesUseCase;

import dagger.Module;
import dagger.Provides;
import hugo.weaving.DebugLog;

/**

 */
@Module
public class NoteListActivityModule {

    @DebugLog
    @Provides
    NoteListView provideMainView(NoteListActivity mainActivity) {
        return mainActivity;
    }

    @DebugLog
    @Provides
    NoteListPresenter provideMainPresenter(NoteListView noteListView, GetNotesUseCase getNotesUseCase) {
        return new NoteListPresenterImpl(noteListView, getNotesUseCase);
    }
}
