package com.tony.tang.note.remote;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
@AutoValue
public abstract class ParseDate {

    public static TypeAdapter<ParseDate> typeAdapter(Gson gson) {
        return new AutoValue_ParseDate.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return new AutoValue_ParseDate.Builder().type("Date");
    }

    @SerializedName("__type")
    public abstract String type();

    @SerializedName("iso")
    public abstract String iso();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder type(String type);

        public abstract Builder iso(String iso);

        public abstract ParseDate build();
    }
}