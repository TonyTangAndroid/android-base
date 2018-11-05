package com.tony.tang.movie;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class AppConfig {

    public static Builder builder() {
        return new AutoValue_AppConfig.Builder();
    }

    public abstract String apiKey();

    public abstract String serverUrl();

    public abstract long inMemoryTtl();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder apiKey(String apiKey);

        public abstract Builder serverUrl(String serverUrl);

        public abstract Builder inMemoryTtl(long inMemoryTtl);

        public abstract AppConfig build();
    }
}
