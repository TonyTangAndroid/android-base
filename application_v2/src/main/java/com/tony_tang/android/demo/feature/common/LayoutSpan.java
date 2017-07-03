package com.tony_tang.android.demo.feature.common;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({SpanType.FULL,
        SpanType.HALF,
        SpanType.ONE_THIRD,
        SpanType.QUARTER})
@Retention(RetentionPolicy.SOURCE)
public @interface LayoutSpan {
}
