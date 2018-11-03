package com.tony_tang.android.demo.common.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jordifierro.androidbase.domain.entity.ParseACLJsonAdapter;
import com.jordifierro.androidbase.domain.entity.ParsePermissionWrapper;
import com.tony_tang.android.demo.common.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;
import hugo.weaving.DebugLog;
import retrofit2.converter.gson.GsonConverterFactory;

@DebugLog
@Module
public class GsonModule {


    @ApplicationScope
    @Provides
    GsonConverterFactory getFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @ApplicationScope
    @Provides
    GsonBuilder gsonBuilder() {
        return new GsonBuilder();
    }

    @ApplicationScope
    @Provides
    Gson getGson(GsonBuilder gsonBuilder) {
        return gsonBuilder.registerTypeAdapter(ParsePermissionWrapper.class, new ParseACLJsonAdapter())
                .create();
    }


}
