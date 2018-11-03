package com.tony_tang.android.demo.presentation.presenter.base;

import com.jordifierro.androidbase.domain.interactor.SingleUseCase;
import com.tony_tang.android.demo.presentation.view.base.CleanView;

import io.reactivex.observers.DisposableSingleObserver;

public abstract class BasePresenter implements Presenter {

    private CleanView cleanView;
    private SingleUseCase[] useCases;

    public BasePresenter(CleanView cleanView, SingleUseCase... useCases) {
        this.cleanView = cleanView;
        this.useCases = useCases;
    }

    @Override
    public void create() {
        cleanView.initUI();
    }


    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        if (useCases != null && useCases.length > 0) {
            for (SingleUseCase useCase : useCases) {
                useCase.unsubscribe();
            }
        }
        cleanView = null;
    }

    public void handleError(Throwable error) {
        cleanView.handleError(error);
    }


    public CleanView getCleanView() {
        return cleanView;
    }


    protected abstract class BaseSubscriber<T> extends DisposableSingleObserver<T> {

        @Override
        public void onError(Throwable e) {
            BasePresenter.this.handleError(e);
        }
    }
}
