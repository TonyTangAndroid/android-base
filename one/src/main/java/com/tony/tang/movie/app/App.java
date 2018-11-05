package com.tony.tang.movie.app;

import android.app.Application;

import com.akaita.java.rxjava2debug.RxJava2Debug;
import com.tony.tang.movie.BuildConfig;
import com.tony.tang.movie.TimberLogUtil;

public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = create();
        appComponent.inject(this);
        TimberLogUtil.initLog();
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
                .inMemoryTtl(15 * 1000)
                .build();
    }


    public AppComponent appComponent() {
        return appComponent;
    }

}
