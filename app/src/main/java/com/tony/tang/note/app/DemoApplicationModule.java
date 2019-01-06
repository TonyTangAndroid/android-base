package com.tony.tang.note.app;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;

@Module
public class DemoApplicationModule {

    @Reusable
    @Provides
    Context provideContext(Application application) {
        return application;
    }

}
