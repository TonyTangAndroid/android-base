package com.tony_tang.android.demo.common;

import android.app.Activity;
import android.app.Fragment;

import javax.inject.Inject;

import androidx.multidex.MultiDexApplication;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasFragmentInjector;

public class DemoApplication extends MultiDexApplication implements HasActivityInjector, HasFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    public DemoApplicationComponent applicationComponent() {
        return demoApplicationComponent;
    }

    private DemoApplicationComponent demoApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        demoApplicationComponent = DaggerDemoApplicationComponent
                .builder()
                .application(this)
                .build();
        demoApplicationComponent
                .inject(this);

    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<Fragment> fragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
