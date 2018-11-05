package com.tony.tang.movie.domain;

import io.reactivex.Scheduler;

public interface PostThread {
    Scheduler getScheduler();
}
