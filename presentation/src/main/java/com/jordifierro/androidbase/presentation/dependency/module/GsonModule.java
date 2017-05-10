package com.jordifierro.androidbase.presentation.dependency.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jordifierro.androidbase.domain.entity.ParseACLJsonAdapter;
import com.jordifierro.androidbase.domain.entity.ParsePermissionWrapper;
import com.jordifierro.androidbase.presentation.dependency.ApplicationScope;

import dagger.Module;
import dagger.Provides;
import hugo.weaving.DebugLog;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class GsonModule {

    @Provides
    //@DebugLog
    @ApplicationScope
    GsonConverterFactory getFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    //@DebugLog
    @Provides
    @ApplicationScope
    Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(ParsePermissionWrapper.class, new ParseACLJsonAdapter())
                .create();
    }


}
