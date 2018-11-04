package com.jordifierro.androidbase.domain.entity;


import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;

@AutoValue
public abstract class NoteEntity {

    public static TypeAdapter<NoteEntity> typeAdapter(Gson gson) {
        return new AutoValue_NoteEntity.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return new AutoValue_NoteEntity.Builder();
    }

    @Nullable
    @SerializedName("objectId")
    public abstract String objectId();

    @Nullable
    @SerializedName("title")
    public abstract String title();

    @Nullable
    @SerializedName("content")
    public abstract String content();

    @Nullable
    @SerializedName("createdAt")
    public abstract String createdAt();

    @Nullable
    @SerializedName("updatedAt")
    public abstract String updatedAt();

    @Nullable
    @SerializedName("ACL")
    public abstract PermissionItemList ACL();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder objectId(String objectId);

        public abstract Builder title(String title);

        public abstract Builder content(String content);

        public abstract Builder createdAt(String createdAt);

        public abstract Builder updatedAt(String updatedAt);

        public abstract Builder ACL(PermissionItemList ACL);

        public abstract NoteEntity build();
    }
}
