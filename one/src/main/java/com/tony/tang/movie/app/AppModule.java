package com.tony.tang.movie.app;

import android.app.Application;
import android.content.Context;

import com.tony.tang.movie.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
class AppModule {

    @AppScope
    @Provides
    Context provideContext(Application application) {
        return application;
    }

}
