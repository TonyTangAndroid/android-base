package com.tony.tang.note.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jordifierro.androidbase.domain.entity.ArsenalAdapterFactory;
import com.jordifierro.androidbase.domain.entity.PermissionItemList;

public class WrapperGsonHelper {

    public WrapperGsonHelper() {
    }

    public static Gson build() {
        return new WrapperGsonHelper().create();
    }

    private Gson create() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapterFactory(WrapperAdapterFactory.create());
        gsonBuilder.registerTypeAdapterFactory(ArsenalAdapterFactory.create());
        gsonBuilder.registerTypeAdapter(PermissionItemList.class, new ParseACLJsonAdapter());
        return gsonBuilder.create();
    }
}