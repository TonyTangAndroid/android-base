package com.tony.tang.note.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.tony.tang.note.app.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class PrefModule {

    private static final String SHARED_PACKAGE = "base_shared_preferences";

    @Provides
    @ApplicationScope
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PACKAGE, Context.MODE_PRIVATE);
    }
}