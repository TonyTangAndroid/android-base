package com.tony_tang.android.demo.common.module;

import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.domain.executor.UIThread;
import com.tony_tang.android.demo.common.module.source.JobExecutor;
import com.tony_tang.android.demo.common.module.source.MainThread;
import com.tony_tang.android.demo.common.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ThreadModule {

    @Provides
    @ApplicationScope
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @ApplicationScope
    UIThread providePostExecutionThread(MainThread mainThread) {
        return mainThread;
    }
}
