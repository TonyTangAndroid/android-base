package com.tony_tang.android.demo.common.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jordifierro.androidbase.domain.entity.ParseACLJsonAdapter;
import com.jordifierro.androidbase.domain.entity.ParsePermissionWrapper;
import com.tony_tang.android.demo.common.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class GsonModule {

    @Provides
    @ApplicationScope
    GsonConverterFactory getFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }


    @Provides
    @ApplicationScope
    Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(ParsePermissionWrapper.class, new ParseACLJsonAdapter())
                .create();
    }


}
