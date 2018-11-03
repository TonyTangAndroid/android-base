package com.tony_tang.android.demo.feature.note_list_paging;

import com.jordifierro.androidbase.domain.interactor.note.DeleteNoteUseCase;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;

class NotePagingListPresenter {

    private final NotePagingUI notePagingUI;
    private final DeleteNoteUseCase deleteNoteUseCase;

    @Inject
    public NotePagingListPresenter(NotePagingUI notePagingUI, DeleteNoteUseCase deleteNoteUseCase) {
        this.notePagingUI = notePagingUI;
        this.deleteNoteUseCase = deleteNoteUseCase;
    }

    void destroy() {
        deleteNoteUseCase.unsubscribe();
    }

    void delete(String noteObjectId) {
        deleteNoteUseCase.setParams(noteObjectId).execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                notePagingUI.handleError(e);
            }
        });
    }

    public interface NotePagingUI {

        void handleError(Throwable e);
    }


}
