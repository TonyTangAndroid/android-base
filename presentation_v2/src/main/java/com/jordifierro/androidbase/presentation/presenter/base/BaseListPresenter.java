package com.jordifierro.androidbase.presentation.presenter.base;

import com.jordifierro.androidbase.domain.interactor.UseCase;
import com.jordifierro.androidbase.presentation.view.base.BaseListView;

import java.util.List;


public abstract class BaseListPresenter extends BasePresenter implements Presenter {


    private BaseListView baseListView;

    public BaseListPresenter(BaseListView baseListView,
                             UseCase... useCases) {
        super(baseListView, useCases);
        this.baseListView = baseListView;
    }

    public BaseListView getBaseListView() {
        return baseListView;
    }

    public abstract void loadData();

    public abstract void refreshData();

    public abstract void loadMoreData();


    public class BaseListSubscriber extends BaseSubscriber<List<?>> {

        @Override
        public void onComplete() {
            baseListView.onRetrievingDataCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            baseListView.onRetrievingDataCompleted();
        }

        @Override
        public void onNext(List<?> entityList) {
            BaseListPresenter.this.baseListView.showEntityList(entityList);
        }
    }


}
