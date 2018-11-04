package com.jordifierro.androidbase.domain.entity;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import io.reactivex.annotations.Nullable;

@AutoValue
public abstract class ParsePermission {


    public static TypeAdapter<ParsePermission> typeAdapter(Gson gson) {
        return new AutoValue_ParsePermission.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return new AutoValue_ParsePermission.Builder();
    }

    @SerializedName("read")
    public abstract boolean read();

    @SerializedName("write")
    public abstract boolean write();

    @Nullable
    @SerializedName("objectId")
    public abstract String objectId();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder read(boolean read);

        public abstract Builder write(boolean write);

        public abstract Builder objectId(String objectId);

        public abstract ParsePermission build();
    }
}