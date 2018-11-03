package com.tony_tang.android.demo.common;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module//申明你的module
public class DemoApplicationModule {

    //1, 申明你的module，你可以提供什么东西。
    @Provides//你可以提供什么东西。
    Context provideContext(Application application) {
        //4101
        return application;
    }

}
