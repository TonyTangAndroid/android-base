package com.tony.tang.movie;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @AppScope
    @Provides
    Context provideContext(Application application) {
        return application;
    }

}
