package com.jordifierro.androidbase.presentation.presenter;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.interactor.note.GetNoteUseCase;
import com.jordifierro.androidbase.presentation.view.BaseView;
import com.jordifierro.androidbase.presentation.view.NoteDetailView;

import javax.inject.Inject;

import hugo.weaving.DebugLog;

public class NoteDetailPresenter extends BasePresenter implements Presenter {

    NoteDetailView noteDetailView;
    private GetNoteUseCase getNoteUseCase;

    //@DebugLog
    @Inject
    public NoteDetailPresenter(GetNoteUseCase getNoteUseCase) {
        super(getNoteUseCase);
        this.getNoteUseCase = getNoteUseCase;
    }

    @Override
    public void initWithView(BaseView view) {
        super.initWithView(view);
        this.noteDetailView = (NoteDetailView) view;
    }

    @Override
    public void resume() {
        this.showLoader();
        this.getNoteUseCase.setParams(this.noteDetailView.getNoteObjectId());
        this.getNoteUseCase.execute(new NoteDetailSubscriber());
    }

    @Override
    public void destroy() {
        super.destroy();
        this.noteDetailView = null;
    }

    protected class NoteDetailSubscriber extends BaseSubscriber<NoteEntity> {

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            NoteDetailPresenter.this.noteDetailView.close();
        }

        @Override
        public void onNext(NoteEntity note) {
            NoteDetailPresenter.this.hideLoader();
            NoteDetailPresenter.this.noteDetailView.showNote(note);
        }
    }

}
