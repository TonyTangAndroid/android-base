package com.jordifierro.androidbase.presentation.presenter;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.interactor.note.GetNotesUseCase;
import com.jordifierro.androidbase.presentation.view.CleanView;
import com.jordifierro.androidbase.presentation.view.NotesViewPagerView;

import java.util.List;

import javax.inject.Inject;


public class NotesViewPagerPresenter extends BasePresenter implements Presenter {

    private NotesViewPagerView notesView;
    private GetNotesUseCase getNotesUseCase;

    @Inject

    public NotesViewPagerPresenter(GetNotesUseCase getNotesUseCase) {
        super(getNotesUseCase);
        this.getNotesUseCase = getNotesUseCase;
    }

    @Override
    protected NotesViewPagerView getCleanView() {
        return notesView;
    }

    @Override
    public void bindPresenter(CleanView view) {
        this.notesView = (NotesViewPagerView) view;
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
            NotesViewPagerPresenter.this.hideLoader();
            NotesViewPagerPresenter.this.notesView.showNotes(notes);
        }
    }


}
