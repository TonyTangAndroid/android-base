package com.tony_tang.android.demo.feature.note_list;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.interactor.note.GetNotesUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**

 */

public class NoteListPresenterImpl implements NoteListPresenter {
    NoteListView noteListView;
    GetNotesUseCase apiService;

    @Inject
    public NoteListPresenterImpl(NoteListView noteListView, GetNotesUseCase getNotesUseCase) {
        this.noteListView = noteListView;
        this.apiService = getNotesUseCase;
    }

    public void loadData() {
        apiService.execute(new DisposableObserver<List<NoteEntity>>() {
            @Override
            public void onNext(@NonNull List<NoteEntity> noteEntityList) {
                noteListView.showNoteEntityList(noteEntityList);

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }
}
