package com.jordifierro.androidbase.domain.interactor;


import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.executor.UIThread;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;

public abstract class CompletableUseCase {

    private final ThreadExecutor threadExecutor;
    private final UIThread UIThread;

    protected Disposable disposable = Disposables.empty();

    protected CompletableUseCase(ThreadExecutor threadExecutor, UIThread UIThread) {
        this.threadExecutor = threadExecutor;
        this.UIThread = UIThread;
    }

    protected abstract Completable build();

    public <S extends CompletableObserver & Disposable> void execute(S useCaseDisposable) {
        this.disposable = this.build()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(UIThread.getScheduler())
                .subscribeWith(useCaseDisposable);
    }

    public void unsubscribe() {
        if (!this.disposable.isDisposed()) {
            this.disposable.dispose();
        }
    }

    public boolean isUnsubscribed() {
        return this.disposable.isDisposed();
    }

}
