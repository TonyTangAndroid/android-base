package com.jordifierro.androidbase.application.dependency.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.jordifierro.androidbase.application.dependency.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPreferenceModule {

    private static final String SHARED_PACKAGE = "base_shared_preferences";

    @Provides
    @ApplicationScope
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PACKAGE, Context.MODE_PRIVATE);
    }
}