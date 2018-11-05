package com.tony.tang.movie;


import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Nullable;

@AutoValue
abstract class ErrorBean {

    public static TypeAdapter<ErrorBean> typeAdapter(Gson gson) {
        return new AutoValue_ErrorBean.GsonTypeAdapter(gson);
    }

    @Nullable
    @SerializedName("status_message")
    public abstract String message();

    @SerializedName("status_code")
    public abstract int code();

    @Nullable
    @SerializedName("errors")
    public abstract List<String> errors();
}
