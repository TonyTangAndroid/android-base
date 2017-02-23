package com.jordifierro.androidbase.presentation.dependency.module;

import com.jordifierro.androidbase.data.net.interceptor.HttpInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class OkHttpInterceptorModule {


    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }


    @Provides
    @Singleton
    HttpInterceptor provideHttpInterceptor() {
        return new HttpInterceptor();
    }
}
