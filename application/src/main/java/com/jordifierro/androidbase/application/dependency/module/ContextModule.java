package com.jordifierro.androidbase.application.dependency.module;

import android.content.Context;

import com.jordifierro.androidbase.application.BaseApplication;
import com.jordifierro.androidbase.application.dependency.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {


    private final Context applicationContext;

    public ContextModule(BaseApplication baseApplication) {
        this.applicationContext = baseApplication.getApplicationContext();
    }

    @Provides
    @ApplicationScope
    Context provideApplicationContext() {
        return this.applicationContext;
    }


}
