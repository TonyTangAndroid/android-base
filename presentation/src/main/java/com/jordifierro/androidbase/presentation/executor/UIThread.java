package com.jordifierro.androidbase.presentation.executor;

import com.jordifierro.androidbase.domain.executor.PostExecutionThread;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;


public class UIThread implements PostExecutionThread {

    //@DebugLog
    @Inject
    public UIThread() {
    }

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }

}