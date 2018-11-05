package com.tony.tang.note.app;

import android.app.Application;

import com.akaita.java.rxjava2debug.RxJava2Debug;

public class App extends Application {

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
}
