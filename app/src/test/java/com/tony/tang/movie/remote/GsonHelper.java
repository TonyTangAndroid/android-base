package com.tony.tang.movie.remote;

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
        gsonBuilder.registerTypeAdapterFactory(GsonAdapterFactory.create());
        return gsonBuilder.create();
    }
}