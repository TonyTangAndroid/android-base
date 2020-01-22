package com.jordifierro.androidbase.presentation;

import android.app.Application;
import android.content.Context;

import androidx.test.runner.AndroidJUnitRunner;

public class TestMockerRunner extends AndroidJUnitRunner {

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context)
        throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, TestMockerApplication.class.getName(), context);
    }
}
