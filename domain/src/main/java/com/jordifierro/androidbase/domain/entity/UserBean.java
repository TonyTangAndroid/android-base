package com.jordifierro.androidbase.domain.entity;


import com.google.auto.value.AutoValue;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;

@AutoValue
public abstract class UserBean {

    public static Builder builder() {
        return new AutoValue_UserBean.Builder();
    }

    @Nullable
    @SerializedName("email")
    public abstract String email();

    @Nullable
    @SerializedName("authToken")
    public abstract String authToken();

    @Nullable
    @SerializedName("password")
    public abstract String password();

    @Nullable
    @SerializedName("passwordConfirmation")
    public abstract String passwordConfirmation();

    @Nullable
    @SerializedName("newPassword")
    public abstract String newPassword();

    @Nullable
    @SerializedName("newPasswordConfirmation")
    public abstract String newPasswordConfirmation();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder email(String email);

        public abstract Builder authToken(String authToken);

        public abstract Builder password(String password);

        public abstract Builder passwordConfirmation(String passwordConfirmation);

        public abstract Builder newPassword(String newPassword);

        public abstract Builder newPasswordConfirmation(String newPasswordConfirmation);

        public abstract UserBean build();
    }
}
