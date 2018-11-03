package com.tony_tang.android.demo.common.module;

import android.content.SharedPreferences;

import com.jordifierro.androidbase.data.repository.UserCacheImpl;
import com.jordifierro.androidbase.domain.cache.UserCache;
import com.tony_tang.android.demo.common.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class CacheModule {

    @Provides
    @ApplicationScope
    UserCache provideUserCache(SharedPreferences sharedPreferences) {
        return new UserCacheImpl(sharedPreferences);
    }


}
