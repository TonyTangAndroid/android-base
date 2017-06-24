package com.jordifierro.androidbase.application;

import android.app.Application;

import com.jordifierro.androidbase.application.dependency.component.ApplicationComponent;
import com.jordifierro.androidbase.application.dependency.component.DaggerActivityComponent;
import com.jordifierro.androidbase.application.dependency.component.DaggerApplicationComponent;
import com.jordifierro.androidbase.application.dependency.component.FragmentInjector;
import com.jordifierro.androidbase.application.dependency.module.ContextModule;


public class BaseApplication extends Application {

    protected ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
    }

    protected void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    public FragmentInjector getFragmentInjector() {
        return DaggerActivityComponent.builder()
                .applicationComponent(this.applicationComponent).build();
    }

}
