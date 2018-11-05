package com.tony.tang.movie;

import io.reactivex.Scheduler;

public interface UIThread {
    public Scheduler getScheduler();
}
