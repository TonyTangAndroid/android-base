package com.jordifierro.androidbase.application;

import com.jordifierro.androidbase.application.BaseApplication;
import com.jordifierro.androidbase.application.dependency.component.FragmentInjector;



public class TestMockerApplication extends BaseApplication {

    @Override
    public FragmentInjector getFragmentInjector() {
        throw new RuntimeException("");
    }
}
