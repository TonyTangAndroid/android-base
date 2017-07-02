package com.jordifierro.androidbase.presentation.presenter;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.interactor.note.ClearNoteListUseCase;
import com.jordifierro.androidbase.domain.interactor.note.GenerateNoteListUseCase;
import com.jordifierro.androidbase.domain.interactor.note.GetNotesUseCase;
import com.jordifierro.androidbase.presentation.view.NoteListView;

import java.util.List;

import javax.inject.Inject;


public class NoteListPresenter extends BasePresenter implements Presenter {

    private NoteListView noteListView;
    private GetNotesUseCase getNotesUseCase;
    private ClearNoteListUseCase clearNoteListUseCase;
    private GenerateNoteListUseCase generateNoteListUseCase;

    @Inject
    public NoteListPresenter(NoteListView noteListView,
                             GetNotesUseCase getNotesUseCase,
                             ClearNoteListUseCase clearNoteListUseCase,
                             GenerateNoteListUseCase generateNoteListUseCase) {
        super(noteListView, getNotesUseCase, generateNoteListUseCase);
        this.noteListView = noteListView;
        this.getNotesUseCase = getNotesUseCase;
        this.clearNoteListUseCase = clearNoteListUseCase;
        this.generateNoteListUseCase = generateNoteListUseCase;
    }

    @Override
    public void resume() {
        loadData();
    }

    public void loadData() {
        this.showLoader();
        this.getNotesUseCase.execute(new NoteListSubscriber());
    }

    public void refreshData() {
        this.getNotesUseCase.resetQueryParam().execute(new NoteListSubscriber());
    }

    public void loadMoreData() {
        this.getNotesUseCase.execute(new NoteListSubscriber());
    }


    public void generateNotes() {
        noteListView.showProcessing();
        generateNoteListUseCase.execute(new GenerateNoteListCountSubscriber());
    }

    public void clearNotes() {
        noteListView.showProcessing();
        clearNoteListUseCase.execute(new ClearNoteListCountSubscriber());
    }

    protected class NoteListSubscriber extends BaseSubscriber<List<NoteEntity>> {

        @Override
        public void onNext(List<NoteEntity> notes) {
            NoteListPresenter.this.hideLoader();
            NoteListPresenter.this.noteListView.showNoteEntityList(notes);
        }
    }

    protected class ClearNoteListCountSubscriber extends BaseSubscriber<Long> {

    }

    protected class GenerateNoteListCountSubscriber extends BaseSubscriber<Integer> {
    }


}
