package com.jordifierro.androidbase.presentation.dependency.module;

import com.jordifierro.androidbase.data.net.interceptor.HttpInterceptor;
import com.jordifierro.androidbase.presentation.dependency.ApplicationScope;

import dagger.Module;
import dagger.Provides;
import hugo.weaving.DebugLog;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class OkHttpInterceptorModule {


	@Provides
	@DebugLog
	@ApplicationScope
	HttpLoggingInterceptor provideHttpLoggingInterceptor() {
		final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
		return interceptor;
	}


	@Provides
	@ApplicationScope
	HttpInterceptor provideHttpInterceptor() {
		return new HttpInterceptor();
	}
}
