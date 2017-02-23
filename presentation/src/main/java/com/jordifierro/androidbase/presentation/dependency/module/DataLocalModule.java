package com.jordifierro.androidbase.presentation.dependency.module;

import android.content.SharedPreferences;

import com.jordifierro.androidbase.data.repository.SessionDataRepository;
import com.jordifierro.androidbase.domain.repository.SessionRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {SharedPreferenceModule.class})
public class DataLocalModule {

    @Provides
    @Singleton
    SessionRepository provideSessionRepository(SharedPreferences sharedPreferences) {
        return new SessionDataRepository(sharedPreferences);
    }


}
