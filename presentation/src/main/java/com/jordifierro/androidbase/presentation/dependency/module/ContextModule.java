package com.jordifierro.androidbase.presentation.dependency.module;

import android.content.Context;

import com.jordifierro.androidbase.presentation.BaseApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {


    private final Context applicationContext;

    public ContextModule(BaseApplication baseApplication) {
        this.applicationContext = baseApplication.getApplicationContext();
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.applicationContext;
    }


}
