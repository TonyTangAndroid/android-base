package com.tony.tang.note.remote;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.robohorse.robopojogenerator")
@AutoValue
public abstract class ParseOperator {

    public static TypeAdapter<ParseOperator> typeAdapter(Gson gson) {
        return new AutoValue_ParseOperator.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return new AutoValue_ParseOperator.Builder();
    }

    @Nullable
    @SerializedName("$gt")
    public abstract ParseDate greatThan();

    @Nullable
    @SerializedName("$lte")
    public abstract ParseDate lessThanOrEqual();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder greatThan(ParseDate greatThan);

        public abstract Builder lessThanOrEqual(ParseDate lessThanOrEqual);

        public abstract ParseOperator build();
    }
}