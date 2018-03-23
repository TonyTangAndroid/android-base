package com.tony_tang.android.demo.common.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.tony_tang.android.demo.common.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPreferenceModule {

    private static final String SHARED_PACKAGE = "base_shared_preferences";

    @Provides
    //@ApplicationScope
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PACKAGE, Context.MODE_PRIVATE);
    }
}
