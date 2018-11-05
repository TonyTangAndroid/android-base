package com.tony.tang.movie.app;

import com.tony.tang.movie.AppScope;
import com.tony.tang.movie.domain.PostThread;
import com.tony.tang.movie.domain.ThreadExecutor;

import dagger.Module;
import dagger.Provides;

@Module
class ThreadModule {

    @Provides
    @AppScope
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @AppScope
    PostThread providePostExecutionThread(MainThread mainThread) {
        return mainThread;
    }
}
