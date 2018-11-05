package com.tony.tang.movie.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.tony.tang.movie.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
class PrefModule {

    private static final String SHARED_PACKAGE = "base_shared_preferences";

    @Provides
    @AppScope
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PACKAGE, Context.MODE_PRIVATE);
    }
}
