package com.jordifierro.androidbase.presentation.dependency.module;

import com.jordifierro.androidbase.domain.executor.PostExecutionThread;
import com.jordifierro.androidbase.domain.executor.ThreadExecutor;
import com.jordifierro.androidbase.presentation.dependency.ApplicationScope;
import com.jordifierro.androidbase.presentation.executor.JobExecutor;
import com.jordifierro.androidbase.presentation.executor.UIThread;

import dagger.Module;
import dagger.Provides;
import hugo.weaving.DebugLog;

@Module
public class ThreadModule {

	@Provides
	@ApplicationScope
	ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
		return jobExecutor;
	}

	@Provides
	@ApplicationScope
	PostExecutionThread providePostExecutionThread(UIThread uiThread) {
		return uiThread;
	}

}
