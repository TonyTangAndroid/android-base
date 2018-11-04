package com.jordifierro.androidbase.domain.entity;


import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.ArrayList;

@AutoValue
public abstract class ParsePermissionWrapper {

    public static TypeAdapter<ParsePermissionWrapper> typeAdapter(Gson gson) {
        return new AutoValue_ParsePermissionWrapper.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return new AutoValue_ParsePermissionWrapper.Builder();
    }

    public abstract ArrayList<ParsePermission> permissionArrayList();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder permissionArrayList(ArrayList<ParsePermission> permissionArrayList);

        public abstract ParsePermissionWrapper build();
    }
}