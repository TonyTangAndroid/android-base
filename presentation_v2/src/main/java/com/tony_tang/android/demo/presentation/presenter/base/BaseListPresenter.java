package com.tony_tang.android.demo.presentation.presenter.base;

import com.jordifierro.androidbase.domain.interactor.SingleUseCase;
import com.tony_tang.android.demo.presentation.view.base.BaseListView;

import java.util.List;


public abstract class BaseListPresenter extends BasePresenter implements Presenter {


    private BaseListView baseListView;

    public BaseListPresenter(BaseListView baseListView,
                             SingleUseCase... useCases) {
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
        public void onError(Throwable e) {
            super.onError(e);
            baseListView.onRetrievingDataCompleted();
        }

        @Override
        public void onSuccess(List<?> entityList) {
            baseListView.onRetrievingDataCompleted();
            BaseListPresenter.this.baseListView.showEntityList(entityList);
        }
    }


}
