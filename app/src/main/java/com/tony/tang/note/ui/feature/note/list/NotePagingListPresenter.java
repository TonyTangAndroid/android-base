package com.tony.tang.note.ui.feature.note.list;

import com.tony.tang.note.domain.entity.NoteData;
import com.tony.tang.note.domain.interactor.note.DeleteNoteUseCase;
import com.tony.tang.note.domain.interactor.note.UpdateNoteUseCase;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;

class NotePagingListPresenter {

    private final NotePagingUI notePagingUI;
    private final UpdateNoteUseCase updateNoteUseCase;
    private final DeleteNoteUseCase deleteNoteUseCase;

    @Inject
    public NotePagingListPresenter(NotePagingUI notePagingUI,
                                   UpdateNoteUseCase updateNoteUseCase,
                                   DeleteNoteUseCase deleteNoteUseCase) {
        this.notePagingUI = notePagingUI;
        this.updateNoteUseCase = updateNoteUseCase;
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

    public void toggleStatus(NoteData data) {
        updateNoteUseCase.setParams(data).execute(new DisposableCompletableObserver() {

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
