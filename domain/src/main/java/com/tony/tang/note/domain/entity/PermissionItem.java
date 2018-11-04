package com.tony.tang.note.domain.entity;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;

@AutoValue
public abstract class PermissionItem {


    public static TypeAdapter<PermissionItem> typeAdapter(Gson gson) {
        return new AutoValue_PermissionItem.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return new AutoValue_PermissionItem.Builder();
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

        public abstract PermissionItem build();
    }
}