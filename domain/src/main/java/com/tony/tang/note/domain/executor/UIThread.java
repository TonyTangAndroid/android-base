package com.tony.tang.note.domain.executor;

import io.reactivex.Scheduler;

public interface UIThread {
    public Scheduler getScheduler();
}
