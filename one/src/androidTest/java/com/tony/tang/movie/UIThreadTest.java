package com.tony.tang.movie;

import com.google.common.truth.Truth;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import io.reactivex.android.schedulers.AndroidSchedulers;

@RunWith(AndroidJUnit4.class)
public class UIThreadTest {

    @Test
    public void getSchedulerShouldReturn() {
        Truth.assertThat(new MainThread().getScheduler()).isEqualTo(AndroidSchedulers.mainThread());
    }
}
