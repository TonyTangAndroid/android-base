package com.tony_tang.android.demo.common.module;

import com.google.gson.Gson;
import com.jordifierro.androidbase.domain.entity.GsonHelper;
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
        return GsonConverterFactory.create(GsonHelper.build());
    }

    @ApplicationScope
    @Provides
    Gson getGson() {
        return GsonHelper.build();
    }
}
