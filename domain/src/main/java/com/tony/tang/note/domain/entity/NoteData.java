package com.tony.tang.note.domain.entity;


import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import javax.annotation.Nullable;

@AutoValue
public abstract class NoteData {

    public static TypeAdapter<NoteData> typeAdapter(Gson gson) {
        return new AutoValue_NoteData.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return new AutoValue_NoteData.Builder();
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
    @SerializedName("status")
    public abstract Integer status();

    @Nullable
    @SerializedName("createdAt")
    public abstract Date createdAt();

    @Nullable
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

        public abstract Builder status(Integer status);

        public abstract Builder createdAt(Date createdAt);

        public abstract Builder updatedAt(Date updatedAt);

        public abstract Builder ACL(PermissionItemList ACL22);

        public abstract NoteData build();
    }
}
