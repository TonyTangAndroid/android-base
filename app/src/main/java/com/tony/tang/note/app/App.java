package com.tony.tang.note.app;

import android.app.Application;

import com.akaita.java.rxjava2debug.RxJava2Debug;
import com.evernote.android.state.StateSaver;

public class App extends Application {

    private AppComponent appComponent;

    public AppComponent appComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        StateSaver.setEnabledForAllActivitiesAndSupportFragments(this, true);
        appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        RxJava2Debug.enableRxJava2AssemblyTracking();
    }
}
