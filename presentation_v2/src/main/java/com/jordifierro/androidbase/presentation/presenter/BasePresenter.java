package com.jordifierro.androidbase.presentation.presenter;

import com.jordifierro.androidbase.domain.interactor.UseCase;
import com.jordifierro.androidbase.presentation.view.CleanView;

import io.reactivex.observers.DisposableObserver;

public abstract class BasePresenter implements Presenter {

    private UseCase[] useCases;

    public BasePresenter(UseCase... useCases) {
        this.useCases = useCases;
    }


    protected abstract CleanView getCleanView();


    @Override
    public void create(CleanView view) {
        bindPresenter(view);
        initCleanView();

    }

    private void initCleanView() {
        getCleanView().initUI();
    }


    public abstract void bindPresenter(CleanView view);

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        if (useCases != null && useCases.length > 0) {
            for (UseCase useCase : useCases) {
                useCase.unsubscribe();
            }
        }

    }

    public void showLoader() {
        this.getCleanView().showLoader();
    }

    public void hideLoader() {
        this.getCleanView().hideLoader();
    }

    public void handleError(Throwable error) {
        this.getCleanView().handleError(error);
    }

    public void showMessage(String message) {
        this.getCleanView().showMessage(message);
    }

    protected class BaseSubscriber<T> extends DisposableObserver<T> {

        @Override
        public void onComplete() {
            BasePresenter.this.hideLoader();
        }

        @Override
        public void onError(Throwable e) {
            BasePresenter.this.hideLoader();
            BasePresenter.this.handleError(e);
            e.printStackTrace();
        }

        @Override
        public void onNext(T t) {
            BasePresenter.this.hideLoader();
            //BasePresenter.this.showMessage(t.toString());
        }
    }
}
