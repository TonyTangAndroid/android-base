package com.jordifierro.androidbase.presentation;

import com.jordifierro.androidbase.presentation.dependency.component.FragmentInjector;

public class TestMockerApplication extends BaseApplication {

    @Override
    public FragmentInjector getFragmentInjector() {
        throw new RuntimeException("");
    }
}
