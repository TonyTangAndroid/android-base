package com.tony_tang.android.demo.presentation.presenter;

import com.jordifierro.androidbase.domain.interactor.note.ClearNoteListUseCase;
import com.jordifierro.androidbase.domain.interactor.note.GenerateNoteListUseCase;
import com.jordifierro.androidbase.domain.interactor.note.GetNotesUseCase;
import com.tony_tang.android.demo.presentation.presenter.base.BaseListPresenter;
import com.tony_tang.android.demo.presentation.presenter.base.Presenter;
import com.tony_tang.android.demo.presentation.view.base.BaseListView;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;


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

    @Override
    public void loadData() {
        this.showLoader();
        this.getNotesUseCase.resetQueryParam().execute(new BaseListSubscriber());
    }

    @Override
    public void refreshData() {
        this.getNotesUseCase.resetQueryParam().execute(new BaseListSubscriber());
    }

    @Override
    public void loadMoreData() {
        this.getNotesUseCase.execute(new BaseListSubscriber());
    }


    public void generateNotes() {
        getBaseListView().showProcessing();
        generateNoteListUseCase.execute(new GenerateNoteListCountSubscriber());
    }

    public void clearNotes() {
        getBaseListView().showProcessing();
        clearNoteListUseCase.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }


    protected class ClearNoteListCountSubscriber extends BaseSubscriber<Long> {

    }

    protected class GenerateNoteListCountSubscriber extends BaseSubscriber<Integer> {
    }


}
