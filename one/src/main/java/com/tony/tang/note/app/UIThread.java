package com.tony.tang.note.app;

import io.reactivex.Scheduler;

public interface UIThread {
    public Scheduler getScheduler();
}
