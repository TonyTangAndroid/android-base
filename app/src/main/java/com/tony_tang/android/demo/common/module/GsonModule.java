package com.tony_tang.android.demo.common.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jordifierro.androidbase.domain.entity.ArsenalAdapterFactory;
import com.jordifierro.androidbase.data.WrapperGsonHelper;
import com.jordifierro.androidbase.data.ParseACLJsonAdapter;
import com.jordifierro.androidbase.domain.entity.PermissionItemList;
import com.jordifierro.androidbase.data.repository.WrapperAdapterFactory;
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
    GsonConverterFactory getFactory() {
        return GsonConverterFactory.create(WrapperGsonHelper.build());
    }

    @ApplicationScope
    @Provides
    Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapterFactory(WrapperAdapterFactory.create());
        gsonBuilder.registerTypeAdapterFactory(ArsenalAdapterFactory.create());
        gsonBuilder.registerTypeAdapter(PermissionItemList.class, new ParseACLJsonAdapter());
        return gsonBuilder.create();

    }
}
