package com.tony_tang.android.demo.presentation.presenter;

import com.jordifierro.androidbase.domain.interactor.note.ClearNoteListUseCase;
import com.jordifierro.androidbase.domain.interactor.note.GenerateNoteListUseCase;
import com.jordifierro.androidbase.domain.interactor.note.GetNotesUseCase;
import com.tony_tang.android.demo.presentation.presenter.base.BaseListPresenter;
import com.tony_tang.android.demo.presentation.presenter.base.Presenter;
import com.tony_tang.android.demo.presentation.view.base.BaseListView;

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
        this.getNotesUseCase.resetQueryParam().execute(new BaseListSubscriber());
    }

    public void refreshData() {
        this.getNotesUseCase.resetQueryParam().execute(new BaseListSubscriber());
    }

    public void loadMoreData() {
        this.getNotesUseCase.execute(new BaseListSubscriber());
    }


    public void generateNotes() {
        getBaseListView().showProcessing();
        generateNoteListUseCase.execute(new GenerateNoteListCountSubscriber());
    }

    public void clearNotes() {
        getBaseListView().showProcessing();
        clearNoteListUseCase.execute(new ClearNoteListCountSubscriber());
    }


    protected class ClearNoteListCountSubscriber extends BaseSubscriber<Long> {

    }

    protected class GenerateNoteListCountSubscriber extends BaseSubscriber<Integer> {
    }


}
