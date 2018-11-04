package com.jordifierro.androidbase.domain.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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