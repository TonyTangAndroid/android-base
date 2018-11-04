package com.jordifierro.androidbase.domain.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonHelper {

    public GsonHelper() {
    }

    public static Gson build() {
        return new GsonHelper().create();
    }

    private Gson create() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapterFactory(ArsenalAdapterFactory.create());
        gsonBuilder.registerTypeAdapter(ParsePermissionWrapper.class, new ParseACLJsonAdapter());
        return gsonBuilder.create();
    }
}