package com.tony_tang.android.demo.common;

import android.app.Application;
import android.content.Context;

import com.tony_tang.android.demo.common.scope.ApplicationScope;

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
