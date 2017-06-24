package com.jordifierro.androidbase.application.dependency.module;

import android.content.SharedPreferences;

import com.jordifierro.androidbase.application.dependency.ApplicationScope;
import com.jordifierro.androidbase.data.repository.SessionDataRepository;
import com.jordifierro.androidbase.domain.repository.SessionRepository;

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
