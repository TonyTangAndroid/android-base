package com.tony.tang.note.app;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import com.akaita.java.rxjava2debug.RxJava2Debug;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;

public class App extends Application implements HasFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    private AppComponent appComponent;

    public AppComponent applicationComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent
                .builder()
                .application(this)
                .serverUrl(BuildConfig.SERVER_URL)
                .apiKey(BuildConfig.API_KEY)
                .build();
        appComponent
                .inject(this);
        RxJava2Debug.enableRxJava2AssemblyTracking();


    }

    @Override
    public AndroidInjector<Fragment> fragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
