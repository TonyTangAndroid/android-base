package com.tony.tang.note.domain.entity;


import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.ArrayList;

@AutoValue
public abstract class PermissionItemList {

    public static TypeAdapter<PermissionItemList> typeAdapter(Gson gson) {
        return new AutoValue_PermissionItemList.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return new AutoValue_PermissionItemList.Builder();
    }

    public abstract ArrayList<PermissionItem> permissionItemList();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder permissionItemList(ArrayList<PermissionItem> permissionArrayList);

        public abstract PermissionItemList build();
    }
}