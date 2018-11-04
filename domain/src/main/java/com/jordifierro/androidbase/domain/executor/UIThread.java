package com.jordifierro.androidbase.domain.executor;

import io.reactivex.Scheduler;

public interface UIThread {
    public Scheduler getScheduler();
}
