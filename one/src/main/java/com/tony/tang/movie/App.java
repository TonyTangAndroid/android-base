package com.tony.tang.movie;

import android.app.Application;

import com.akaita.java.rxjava2debug.RxJava2Debug;

public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = create();
        appComponent.inject(this);
        RxJava2Debug.enableRxJava2AssemblyTracking();
    }

    private AppComponent create() {
        return DaggerAppComponent
                .builder()
                .application(this)
                .appConfig(appConfig())
                .build();
    }

    private AppConfig appConfig() {
        return AppConfig.builder()
                .apiKey(BuildConfig.API_KEY)
                .serverUrl(BuildConfig.SERVER_URL)
                .inMemoryTtl(10 * 1000)
                .build();
    }


    public AppComponent appComponent() {
        return appComponent;
    }

}
