package com.tony_tang.android.demo.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jordifierro.androidbase.presentation.presenter.base.BasePresenter;

import dagger.android.AndroidInjection;

public abstract class PresenterActivity extends CleanActivity  {

    protected abstract BasePresenter presenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        presenter().create();

    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter().pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter().resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter().destroy();
    }
}
