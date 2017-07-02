package com.jordifierro.androidbase.presentation.presenter;

import com.jordifierro.androidbase.domain.interactor.note.ClearNoteListUseCase;
import com.jordifierro.androidbase.domain.interactor.note.GenerateNoteListUseCase;
import com.jordifierro.androidbase.domain.interactor.note.GetNotesUseCase;
import com.jordifierro.androidbase.presentation.presenter.base.BaseListPresenter;
import com.jordifierro.androidbase.presentation.presenter.base.Presenter;
import com.jordifierro.androidbase.presentation.view.base.BaseListView;

import javax.inject.Inject;


public class NoteListPresenter extends BaseListPresenter implements Presenter {


    private GetNotesUseCase getNotesUseCase;
    private ClearNoteListUseCase clearNoteListUseCase;
    private GenerateNoteListUseCase generateNoteListUseCase;

    @Inject
    public NoteListPresenter(BaseListView noteListView,
                             GetNotesUseCase getNotesUseCase,
                             ClearNoteListUseCase clearNoteListUseCase,
                             GenerateNoteListUseCase generateNoteListUseCase) {
        super(noteListView, getNotesUseCase, generateNoteListUseCase);
        this.getNotesUseCase = getNotesUseCase;
        this.clearNoteListUseCase = clearNoteListUseCase;
        this.generateNoteListUseCase = generateNoteListUseCase;
    }

    public void loadData() {
        this.showLoader();
        this.getNotesUseCase.resetQueryParam().execute(new NoteListSubscriber());
    }

    public void refreshData() {
        this.getNotesUseCase.resetQueryParam().execute(new NoteListSubscriber());
    }

    public void loadMoreData() {
        this.getNotesUseCase.execute(new NoteListSubscriber());
    }


    public void generateNotes() {
        getNoteListView().showProcessing();
        generateNoteListUseCase.execute(new GenerateNoteListCountSubscriber());
    }

    public void clearNotes() {
        getNoteListView().showProcessing();
        clearNoteListUseCase.execute(new ClearNoteListCountSubscriber());
    }


    protected class ClearNoteListCountSubscriber extends BaseSubscriber<Long> {

    }

    protected class GenerateNoteListCountSubscriber extends BaseSubscriber<Integer> {
    }


}
