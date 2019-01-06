package com.tony.tang.note.domain.entity;


import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import javax.annotation.Nullable;

@AutoValue
public abstract class NoteEntity {

    public static TypeAdapter<NoteEntity> typeAdapter(Gson gson) {
        return new AutoValue_NoteEntity.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return new AutoValue_NoteEntity.Builder();
    }

    @SerializedName("objectId")
    public abstract String objectId();

    @SerializedName("title")
    public abstract String title();

    @SerializedName("content")
    public abstract String content();

    @SerializedName("status")
    public abstract int status();

    @SerializedName("createdAt")
    public abstract Date createdAt();

    @SerializedName("updatedAt")
    public abstract Date updatedAt();

    @Nullable
    @SerializedName("ACL")
    public abstract PermissionItemList ACL();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder objectId(String objectId);

        public abstract Builder title(String title);

        public abstract Builder content(String content);

        public abstract Builder status(int status);

        public abstract Builder createdAt(Date createdAt);

        public abstract Builder updatedAt(Date updatedAt);

        public abstract Builder ACL(PermissionItemList ACL22);

        public abstract NoteEntity build();
    }
}
