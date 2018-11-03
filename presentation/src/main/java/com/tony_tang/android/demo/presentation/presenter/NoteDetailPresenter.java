package com.tony_tang.android.demo.presentation.presenter;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.interactor.note.GetNoteUseCase;
import com.tony_tang.android.demo.presentation.presenter.base.BasePresenter;
import com.tony_tang.android.demo.presentation.presenter.base.Presenter;
import com.tony_tang.android.demo.presentation.view.NoteDetailView;

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
