package com.tony.tang.movie.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tony.tang.movie.AppScope;

import dagger.Module;
import dagger.Provides;
import hugo.weaving.DebugLog;
import retrofit2.converter.gson.GsonConverterFactory;

@DebugLog
@Module
public class GsonModule {

    @AppScope
    @Provides
    GsonConverterFactory getFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @AppScope
    @Provides
    Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapterFactory(GsonAdapterFactory.create());
        return gsonBuilder.create();

    }
}
