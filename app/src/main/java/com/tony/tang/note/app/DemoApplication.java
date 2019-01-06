package com.tony.tang.note.app;

import android.app.Application;

import com.akaita.java.rxjava2debug.RxJava2Debug;

public class DemoApplication extends Application {

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
                .build();
        demoApplicationComponent
                .inject(this);
        RxJava2Debug.enableRxJava2AssemblyTracking();
    }
}
