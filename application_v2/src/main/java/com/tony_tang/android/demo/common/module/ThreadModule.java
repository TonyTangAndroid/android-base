package com.tony_tang.android.demo.common.module;

import com.jordifierro.androidbase.domain.executor.PostExecutionThread;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.tony_tang.android.demo.common.scope.ApplicationScope;
import com.tony_tang.android.demo.common.module.source.JobExecutor;
import com.tony_tang.android.demo.common.module.source.UIThread;

import dagger.Module;
import dagger.Provides;

@Module
public class ThreadModule {

    @Provides
    //@ApplicationScope
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    //@ApplicationScope
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

}
