package com.tony.tang.note.app;

import com.tony.tang.note.domain.executor.ThreadExecutor;
import com.tony.tang.note.domain.executor.UIThread;

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
