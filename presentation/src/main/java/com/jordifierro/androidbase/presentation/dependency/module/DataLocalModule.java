package com.jordifierro.androidbase.presentation.dependency.module;

import android.content.SharedPreferences;

import com.jordifierro.androidbase.data.repository.SessionDataRepository;
import com.jordifierro.androidbase.domain.repository.SessionRepository;
import com.jordifierro.androidbase.presentation.dependency.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module(includes = {SharedPreferenceModule.class})
public class DataLocalModule {

    @Provides
    @ApplicationScope
    SessionRepository provideSessionRepository(SharedPreferences sharedPreferences) {
        return new SessionDataRepository(sharedPreferences);
    }


}
