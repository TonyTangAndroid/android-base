package com.jordifierro.androidbase.domain.interactor;


import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.executor.UIThread;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;

public abstract class SingleUseCase<T> {

    private final ThreadExecutor threadExecutor;
    private final UIThread uiThread;

    protected Disposable disposable = Disposables.empty();

    protected SingleUseCase(ThreadExecutor threadExecutor, UIThread uiThread) {
        this.threadExecutor = threadExecutor;
        this.uiThread = uiThread;
    }

    protected abstract Single<T> build();

    public <S extends SingleObserver<T> & Disposable> void execute(S useCaseDisposable) {
        this.disposable = this.build()
                .compose(this::transformError)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(uiThread.getScheduler())
                .subscribeWith(useCaseDisposable);
    }

    private Single<? extends T> transformError(Single<T> single) {
        return single.onErrorResumeNext(this::transformError);
    }

    private SingleSource<? extends T> transformError(Throwable throwable) {
        return Single.error(threadExecutor.map(throwable));
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
