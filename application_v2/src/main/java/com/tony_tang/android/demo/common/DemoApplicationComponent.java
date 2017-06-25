package com.tony_tang.android.demo.common;

import android.app.Application;

import com.tony_tang.android.demo.common.module.DataModule;
import com.tony_tang.android.demo.common.scope.ApplicationScope;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**

 */
@ApplicationScope
@Component(modules = {
        AndroidInjectionModule.class,
        DemoApplicationModule.class,
        DataModule.class,
        DemoActivityInjector.class})
public interface DemoApplicationComponent {

    void inject(DemoApplication app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        DemoApplicationComponent build();
    }
}
