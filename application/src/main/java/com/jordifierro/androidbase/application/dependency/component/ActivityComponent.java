package com.jordifierro.androidbase.application.dependency.component;

import com.jordifierro.androidbase.application.dependency.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class)
public interface ActivityComponent extends FragmentInjector {
}
