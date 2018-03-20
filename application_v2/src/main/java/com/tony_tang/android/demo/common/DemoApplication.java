package com.tony_tang.android.demo.common;

import android.app.Activity;
import android.app.Fragment;
import android.support.multidex.MultiDexApplication;

import com.tony_tang.android.demo.common.module.DaggerGsonComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasFragmentInjector;

public class DemoApplication extends MultiDexApplication implements HasActivityInjector, HasFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerDemoApplicationComponent
                .builder()
                .application(this)
                .gsonComponent(DaggerGsonComponent.create())
                .build()
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
