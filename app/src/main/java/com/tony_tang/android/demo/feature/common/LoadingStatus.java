package com.tony_tang.android.demo.feature.common;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({CleanViewStatus.IDLE,
        CleanViewStatus.LOADING,
        CleanViewStatus.REFRESHING,
        CleanViewStatus.LOADING_MORE})
@Retention(RetentionPolicy.SOURCE)
public @interface LoadingStatus {
}
