package com.tony.tang.note.presenter;

import com.tony.tang.note.domain.entity.NoteData;
import com.tony.tang.note.domain.entity.NoteEntity;
import com.tony.tang.note.domain.interactor.note.GetNoteUseCase;
import com.tony.tang.note.domain.interactor.note.UpdateNoteUseCase;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;


public class NoteDetailPresenter extends BasePresenter implements Presenter {

    private final NoteDetailView noteDetailView;
    private final GetNoteUseCase getNoteUseCase;
    private final UpdateNoteUseCase updateNoteUseCase;


    @Inject
    public NoteDetailPresenter(NoteDetailView noteDetailView,
                               GetNoteUseCase getNoteUseCase, UpdateNoteUseCase updateNoteUseCase) {
        super(noteDetailView, getNoteUseCase);
        this.noteDetailView = noteDetailView;
        this.getNoteUseCase = getNoteUseCase;
        this.updateNoteUseCase = updateNoteUseCase;
    }

    @Override
    public void resume() {
        this.noteDetailView.showLoader();
        this.getNoteUseCase.setParams(this.noteDetailView.getNoteObjectId());
        this.getNoteUseCase.execute(new NoteDetailSubscriber());
    }

    public void toggleChecked(NoteData data) {
        updateNoteUseCase.setParams(data).execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                noteDetailView.handleError(e);

            }
        });
    }

    public interface NoteDetailView extends CleanView {

        void showNote(NoteEntity note);

        String getNoteObjectId();

        void hideLoader();

        void showLoader();
    }

    protected class NoteDetailSubscriber extends BaseSubscriber<NoteEntity> {

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            noteDetailView.hideLoader();
        }

        @Override
        public void onSuccess(NoteEntity note) {
            NoteDetailPresenter.this.noteDetailView.hideLoader();
            NoteDetailPresenter.this.noteDetailView.showNote(note);
        }
    }
}
