package com.tony.tang.note.app;


import io.reactivex.Single;
import io.reactivex.SingleObserver;
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
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(uiThread.getScheduler())
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
