package com.tony_tang.android.demo.feature.common;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;


@AutoValue
public abstract class EmptyViewEntity {

    public static Builder builder() {
        return new AutoValue_EmptyViewEntity.Builder();
    }

    public abstract boolean showLoading();

    public abstract boolean showImage();

    @DrawableRes
    public abstract int imageDrawableRes();

    public abstract boolean showRetry();

    @Nullable
    public abstract String topHint();

    @Nullable
    public abstract String middleHint1();

    @Nullable
    public abstract String middleHint2();

    @Nullable
    public abstract String bottomHint();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder showLoading(boolean showLoading);

        public abstract Builder showRetry(boolean showRetry);

        public abstract Builder topHint(String topHint);

        public abstract Builder middleHint1(String middleHint1);

        public abstract Builder middleHint2(String middleHint2);

        public abstract Builder bottomHint(String bottomHint);

        public abstract Builder showImage(boolean showImage);

        public abstract Builder imageDrawableRes(int imageDrawableRes);

        public abstract EmptyViewEntity build();
    }
}
