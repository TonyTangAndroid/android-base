package com.tony_tang.android.demo.feature.common;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;


@AutoValue
public abstract class FooterViewEntity {

    public static Builder builder() {
        return new AutoValue_FooterViewEntity.Builder();
    }

    public abstract boolean showLoading();

    public abstract boolean showFooterView();

    @Nullable
    public abstract String footerHint();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder showLoading(boolean showLoading);

        public abstract Builder showFooterView(boolean showFooterView);

        public abstract Builder footerHint(String footerHint);

        public abstract FooterViewEntity build();
    }
}
