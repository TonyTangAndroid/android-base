package com.tony_tang.android.demo.common.module;

import com.jordifierro.androidbase.data.repository.NoteInMemoryImpl;
import com.jordifierro.androidbase.data.repository.TokenDataRepository;
import com.jordifierro.androidbase.data.repository.UserCacheImpl;
import com.jordifierro.androidbase.domain.cache.UserCache;
import com.jordifierro.androidbase.domain.repository.TokenRepository;
import com.tony_tang.android.demo.common.scope.ApplicationScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {SharedPreferenceModule.class, DbModule.class})
public abstract class DataLocalModule {

    @Provides
    @ApplicationScope
    static NoteInMemoryImpl badgeCacheMemoryImpl() {
        return new NoteInMemoryImpl(5 * 1000);
    }

    @Binds
    @ApplicationScope
    abstract TokenRepository bindTokenRepository(TokenDataRepository tokenDataRepository);

    @Binds
    @ApplicationScope
    abstract UserCache bindUserCache(UserCacheImpl userCache);


}
