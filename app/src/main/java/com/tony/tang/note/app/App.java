package com.tony.tang.note.app;

import android.app.Application;

import com.akaita.java.rxjava2debug.RxJava2Debug;
import com.evernote.android.state.StateSaver;
import com.hunter.library.debug.HunterLoggerHandler;
import com.tonytangandroid.wood.WoodTree;

import timber.log.Timber;

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
        installLogger();
    }

    private void installLogger() {
        Timber.plant(new WoodTree(this).showNotification(true));
        HunterLoggerHandler.installLogImpl(new HunterLoggerHandler() {
            @Override
            protected void log(String tag, String msg) {
                Timber.v(tag, msg);
            }
        });
    }
}
