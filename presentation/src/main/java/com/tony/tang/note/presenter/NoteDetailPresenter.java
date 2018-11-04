package com.tony.tang.note.presenter;

import com.tony.tang.note.domain.entity.NoteEntity;
import com.tony.tang.note.domain.interactor.note.GetNoteUseCase;

import javax.inject.Inject;


public class NoteDetailPresenter extends BasePresenter implements Presenter {

    private final NoteDetailView noteDetailView;
    private final GetNoteUseCase getNoteUseCase;

    @Inject
    public NoteDetailPresenter(NoteDetailView noteDetailView,
                               GetNoteUseCase getNoteUseCase) {
        super(noteDetailView, getNoteUseCase);
        this.noteDetailView = noteDetailView;
        this.getNoteUseCase = getNoteUseCase;
    }

    @Override
    public void resume() {
        this.noteDetailView.showLoader();
        this.getNoteUseCase.setParams(this.noteDetailView.getNoteObjectId());
        this.getNoteUseCase.execute(new NoteDetailSubscriber());
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
