package com.jordifierro.androidbase.presentation.executor;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.android.schedulers.AndroidSchedulers;

import static junit.framework.TestCase.assertEquals;

@RunWith(AndroidJUnit4.class)
public class UIThreadTest {

    @Test
    public void testExecute() {
        assertEquals(new UIThread().getScheduler(), AndroidSchedulers.mainThread());
    }
}
