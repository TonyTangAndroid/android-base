package com.tony_tang.android.demo.common.module;

import android.content.SharedPreferences;

import com.jordifierro.androidbase.data.repository.SessionDataRepository;
import com.jordifierro.androidbase.domain.repository.SessionRepository;
import com.tony_tang.android.demo.common.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class DataLocalModule {

    @Provides
    @ApplicationScope
    SessionRepository provideSessionRepository(SharedPreferences sharedPreferences) {
        return new SessionDataRepository(sharedPreferences);
    }


}
