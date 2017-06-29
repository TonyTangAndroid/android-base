package com.tony_tang.android.demo.common;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class DemoApplicationModule {

    @Provides
    Context provideContext(Application application) {
        return application;
    }

}
