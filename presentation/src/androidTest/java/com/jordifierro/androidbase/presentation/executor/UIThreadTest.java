package com.jordifierro.androidbase.presentation.executor;

import static junit.framework.TestCase.assertEquals;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import io.reactivex.android.schedulers.AndroidSchedulers;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UIThreadTest {

    @Test
    public void testExecute() {
        assertEquals(new UIThread().getScheduler(), AndroidSchedulers.mainThread());
    }
}
