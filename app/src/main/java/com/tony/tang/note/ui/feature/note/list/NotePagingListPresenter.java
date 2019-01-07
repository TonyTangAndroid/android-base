package com.tony.tang.note.ui.feature.note.list;

import com.tony.tang.note.domain.entity.NoteData;
import com.tony.tang.note.domain.interactor.note.ClearNoteListUseCase;
import com.tony.tang.note.domain.interactor.note.DeleteNoteUseCase;
import com.tony.tang.note.domain.interactor.note.GenerateNoteListUseCase;
import com.tony.tang.note.domain.interactor.note.UpdateNoteUseCase;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;

class NotePagingListPresenter {

    private final NotePagingUI notePagingUI;
    private final UpdateNoteUseCase updateNoteUseCase;
    private final DeleteNoteUseCase deleteNoteUseCase;
    private final ClearNoteListUseCase clearNoteListUseCase;
    private final GenerateNoteListUseCase generateNoteListUseCase;

    @Inject
    public NotePagingListPresenter(NotePagingUI notePagingUI,
                                   UpdateNoteUseCase updateNoteUseCase,
                                   DeleteNoteUseCase deleteNoteUseCase,
                                   ClearNoteListUseCase clearNoteListUseCase,
                                   GenerateNoteListUseCase generateNoteListUseCase) {
        this.notePagingUI = notePagingUI;
        this.updateNoteUseCase = updateNoteUseCase;
        this.deleteNoteUseCase = deleteNoteUseCase;
        this.clearNoteListUseCase = clearNoteListUseCase;
        this.generateNoteListUseCase = generateNoteListUseCase;
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

    public void clear() {
        clearNoteListUseCase.execute(new DisposableCompletableObserver() {
            @Override
            protected void onStart() {
                notePagingUI.showLoading();
            }

            @Override
            public void onComplete() {
                notePagingUI.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                notePagingUI.handleError(e);
            }
        });
    }

    public void generate() {
        generateNoteListUseCase.execute(new DisposableSingleObserver<Integer>() {

            @Override
            public void onSuccess(Integer count) {
                notePagingUI.showCount(count);
            }

            @Override
            public void onError(Throwable e) {
                notePagingUI.handleError(e);
            }
        });
    }

    public interface NotePagingUI {

        void handleError(Throwable e);

        void showLoading();

        void hideLoading();

        void showCount(int count);
    }


}
