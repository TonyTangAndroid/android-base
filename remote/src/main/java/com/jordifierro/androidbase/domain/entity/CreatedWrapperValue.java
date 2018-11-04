package com.jordifierro.androidbase.domain.entity;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class CreatedWrapperValue {

    public static Builder builder() {
        return new AutoValue_CreatedWrapperValue.Builder();
    }

    public abstract String getSessionToken();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setSessionToken(String newSessionToken);

        public abstract CreatedWrapperValue build();
    }
}
