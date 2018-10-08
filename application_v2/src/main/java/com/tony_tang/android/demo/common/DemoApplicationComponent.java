package com.tony_tang.android.demo.common;

import android.app.Application;

import com.tony_tang.android.demo.common.module.CacheModule;
import com.tony_tang.android.demo.common.module.DataModule;
import com.tony_tang.android.demo.common.module.GsonModule;
import com.tony_tang.android.demo.common.module.SharedPreferenceModule;
import com.tony_tang.android.demo.common.module.ThreadModule;
import com.tony_tang.android.demo.common.scope.ApplicationScope;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**

 */
@ApplicationScope
@Component(modules = {
        AndroidInjectionModule.class,
        DemoApplicationModule.class,//小Boss
        SharedPreferenceModule.class,
        ThreadModule.class,
        GsonModule.class,
        DataModule.class,
        CacheModule.class,
        DemoActivityInjector.class,
        DemoFragmentInjector.class})
public interface DemoApplicationComponent {

    void inject(DemoApplication app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        DemoApplicationComponent build();
    }
}
