package com.jordifierro.androidbase.presentation.presenter;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.interactor.note.GetNotesUseCase;
import com.jordifierro.androidbase.presentation.view.NoteListView;

import java.util.List;

import javax.inject.Inject;


public class NoteListPresenter extends BasePresenter implements Presenter {

    private NoteListView noteListView;
    private GetNotesUseCase getNotesUseCase;

    @Inject
    public NoteListPresenter(NoteListView noteListView, GetNotesUseCase getNotesUseCase) {
        super(noteListView, getNotesUseCase);
        this.noteListView = noteListView;
        this.getNotesUseCase = getNotesUseCase;
    }

    @Override
    public void resume() {
        loadData();
    }

    public void loadData() {
        this.showLoader();
        this.getNotesUseCase.execute(new NoteListSubscriber());
    }

    @Override
    public void destroy() {
        super.destroy();
        this.noteListView = null;
    }

    protected class NoteListSubscriber extends BaseSubscriber<List<NoteEntity>> {

        @Override
        public void onNext(List<NoteEntity> notes) {
            NoteListPresenter.this.hideLoader();
            NoteListPresenter.this.noteListView.showNoteEntityList(notes);
        }
    }


}
