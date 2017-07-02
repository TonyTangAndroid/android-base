package com.jordifierro.androidbase.presentation.presenter.base;

import com.jordifierro.androidbase.domain.interactor.UseCase;
import com.jordifierro.androidbase.presentation.view.base.CleanView;

import io.reactivex.observers.DisposableObserver;

public abstract class BasePresenter implements Presenter {

    private CleanView cleanView;
    private UseCase[] useCases;

    public BasePresenter(CleanView cleanView, UseCase... useCases) {
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
            for (UseCase useCase : useCases) {
                useCase.unsubscribe();
            }
        }
        cleanView = null;
    }

    public void showLoader() {
        cleanView.showLoader();
    }

    public void hideLoader() {
        cleanView.hideLoader();
    }

    public void handleError(Throwable error) {
        cleanView.handleError(error);
    }

    public void showMessage(String message) {
        cleanView.showMessage(message);
    }

    public CleanView getCleanView() {
        return cleanView;
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
        }

        @Override
        public void onNext(T t) {
            BasePresenter.this.hideLoader();
        }
    }
}
