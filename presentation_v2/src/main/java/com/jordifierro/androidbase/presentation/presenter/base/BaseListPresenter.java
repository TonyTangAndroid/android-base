package com.jordifierro.androidbase.presentation.presenter.base;

import com.jordifierro.androidbase.domain.interactor.UseCase;
import com.jordifierro.androidbase.presentation.view.base.BaseListView;

import java.util.List;


public abstract class BaseListPresenter extends BasePresenter implements Presenter {


    private BaseListView noteListView;

    public BaseListPresenter(BaseListView noteListView,
                             UseCase... useCases) {
        super(noteListView, useCases);
        this.noteListView = noteListView;
    }

    public BaseListView getNoteListView() {
        return noteListView;
    }

    public abstract void loadData();

    public abstract void refreshData();

    public abstract void loadMoreData();


    public class NoteListSubscriber extends BaseSubscriber<List<?>> {

        @Override
        public void onComplete() {
            noteListView.onRetrievingDataCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            noteListView.onRetrievingDataCompleted();
        }

        @Override
        public void onNext(List<?> notes) {
            BaseListPresenter.this.noteListView.showNoteEntityList(notes);
        }
    }


}
