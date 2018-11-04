package com.jordifierro.androidbase.domain.entity;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class NoteEntitiesWrapper {


    public static TypeAdapter<NoteEntitiesWrapper> typeAdapter(Gson gson) {
        return new AutoValue_NoteEntitiesWrapper.GsonTypeAdapter(gson);
    }


    @SerializedName("results")
    public abstract List<NoteEntity> results();
}
