package com.jordifierro.androidbase.presentation;

import com.jordifierro.androidbase.presentation.dependency.ActivityScope;
import com.jordifierro.androidbase.presentation.view.LoginPresenter;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;

@Module
public class TestMockerModule {

    @Provides
    @ActivityScope
    LoginPresenter provideLoginPresenter() {
        return Mockito.mock(LoginPresenter.class);
    }

}
