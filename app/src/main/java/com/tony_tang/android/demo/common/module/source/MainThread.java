package com.tony_tang.android.demo.common.module.source;

import com.jordifierro.androidbase.domain.executor.UIThread;

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