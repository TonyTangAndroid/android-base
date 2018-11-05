package com.tony.tang.note.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tony.tang.note.app.ApplicationScope;
import com.tony.tang.note.domain.entity.ArsenalAdapterFactory;
import com.tony.tang.note.domain.entity.PermissionItemList;

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
    Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapterFactory(WrapperAdapterFactory.create());
        gsonBuilder.registerTypeAdapterFactory(ArsenalAdapterFactory.create());
        gsonBuilder.registerTypeAdapter(PermissionItemList.class, new ParseACLJsonAdapter());
        return gsonBuilder.create();

    }
}
