package com.tony.tang.note.app;

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
    }

    void delete(long id) {
        deleteNoteUseCase.setParams(id).execute(new DisposableCompletableObserver() {
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
