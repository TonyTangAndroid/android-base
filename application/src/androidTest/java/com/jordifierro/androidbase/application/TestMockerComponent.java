package com.jordifierro.androidbase.application;

import com.jordifierro.androidbase.application.dependency.ActivityScope;
import com.jordifierro.androidbase.application.dependency.component.ApplicationComponent;
import com.jordifierro.androidbase.application.dependency.component.FragmentInjector;

import dagger.Component;


@ActivityScope
@Component(modules = TestMockerModule.class, dependencies = ApplicationComponent.class)
public interface TestMockerComponent extends FragmentInjector {
}
