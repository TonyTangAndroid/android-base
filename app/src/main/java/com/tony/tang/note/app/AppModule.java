package com.tony.tang.note.app;

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
