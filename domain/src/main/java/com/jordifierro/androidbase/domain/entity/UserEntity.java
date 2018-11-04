package com.jordifierro.androidbase.domain.entity;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;

@AutoValue
public abstract class UserEntity {

    public static TypeAdapter<UserEntity> typeAdapter(Gson gson) {
        return new AutoValue_UserEntity.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return new AutoValue_UserEntity.Builder();
    }


    @SerializedName("email")
    public abstract String email();

    @SerializedName("username")
    public abstract String username();

    @SerializedName("objectId")
    public abstract String objectId();

    @SerializedName("createdAt")
    public abstract String createdAt();

    @Nullable
    @SerializedName("password")
    public abstract String password();

    @SerializedName("updatedAt")
    public abstract String updatedAt();

    @SerializedName("sessionToken")
    public abstract String sessionToken();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder email(String email);

        public abstract Builder username(String username);

        public abstract Builder objectId(String objectId);

        public abstract Builder password(String password);

        public abstract Builder createdAt(String createdAt);

        public abstract Builder updatedAt(String updatedAt);

        public abstract Builder sessionToken(String sessionToken);

        public abstract UserEntity build();
    }
}
