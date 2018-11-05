package com.tony.tang.movie;

import dagger.Module;
import dagger.Provides;

@Module
public class ThreadModule {

    @Provides
    @AppScope
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @AppScope
    UIThread providePostExecutionThread(MainThread mainThread) {
        return mainThread;
    }
}
