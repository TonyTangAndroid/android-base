package com.tony.tang.note.app;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class DemoApplicationModule {

    @ApplicationScope
    @Provides
    Context provideContext(Application application) {
        return application;
    }

}
