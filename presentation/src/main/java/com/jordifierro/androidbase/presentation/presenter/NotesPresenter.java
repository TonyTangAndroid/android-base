package com.jordifierro.androidbase.presentation.presenter;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.interactor.note.GetNotesUseCase;
import com.jordifierro.androidbase.presentation.view.CleanView;
import com.jordifierro.androidbase.presentation.view.NotesView;

import java.util.List;

import javax.inject.Inject;


public class NotesPresenter extends BasePresenter implements Presenter {

    private NotesView notesView;
    private GetNotesUseCase getNotesUseCase;

    @Inject

    public NotesPresenter(GetNotesUseCase getNotesUseCase) {
        super(getNotesUseCase);
        this.getNotesUseCase = getNotesUseCase;
    }

    @Override
    public void bindPresenter(CleanView view) {
        this.notesView = (NotesView) view;
    }

    @Override
    protected NotesView getCleanView() {
        return notesView;
    }


    @Override
    public void resume() {
        this.showLoader();
        this.getNotesUseCase.execute(new NotesSubscriber());
    }

    @Override
    public void destroy() {
        super.destroy();
        this.notesView = null;
    }

    protected class NotesSubscriber extends BaseSubscriber<List<NoteEntity>> {

        @Override
        public void onNext(List<NoteEntity> notes) {
            NotesPresenter.this.hideLoader();
            NotesPresenter.this.notesView.showNotes(notes);
        }
    }


}
