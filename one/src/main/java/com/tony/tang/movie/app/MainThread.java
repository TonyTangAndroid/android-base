package com.tony.tang.movie.app;

import com.tony.tang.movie.domain.PostThread;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

class MainThread implements PostThread {

    @Inject
    public MainThread() {
    }

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }

}