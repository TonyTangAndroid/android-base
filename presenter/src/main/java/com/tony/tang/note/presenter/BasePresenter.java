package com.tony.tang.note.presenter;

import com.tony.tang.note.domain.interactor.CompletableUseCase;
import com.tony.tang.note.domain.interactor.SingleUseCase;

import io.reactivex.observers.DisposableSingleObserver;

public abstract class BasePresenter implements Presenter {

    private CleanView cleanView;
    private SingleUseCase[] useCases;
    private CompletableUseCase[] completableUseCases;

    public BasePresenter(CleanView cleanView, SingleUseCase... useCases) {
        this.cleanView = cleanView;
        this.useCases = useCases;
    }

    public BasePresenter(CleanView cleanView, CompletableUseCase... useCases) {
        this.cleanView = cleanView;
        this.completableUseCases = useCases;
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
        if (completableUseCases != null && completableUseCases.length > 0) {
            for (CompletableUseCase useCase : completableUseCases) {
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
