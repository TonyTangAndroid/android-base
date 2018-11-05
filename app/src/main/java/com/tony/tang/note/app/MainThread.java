package com.tony.tang.note.app;

import com.tony.tang.note.domain.executor.UIThread;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;


public class MainThread implements UIThread {


    @Inject
    public MainThread() {
    }

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }

}