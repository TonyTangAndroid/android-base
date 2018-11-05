package com.tony.tang.note.app;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import com.akaita.java.rxjava2debug.RxJava2Debug;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;

public class DemoApplication extends Application implements HasFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    private DemoApplicationComponent demoApplicationComponent;

    public DemoApplicationComponent applicationComponent() {
        return demoApplicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        demoApplicationComponent = DaggerDemoApplicationComponent
                .builder()
                .application(this)
                .serverUrl(BuildConfig.SERVER_URL)
                .apiKey(BuildConfig.API_KEY)
                .build();
        demoApplicationComponent
                .inject(this);
        RxJava2Debug.enableRxJava2AssemblyTracking();


    }

    @Override
    public AndroidInjector<Fragment> fragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
