package com.tony.tang.note.data;

import com.jordifierro.androidbase.domain.repository.TokenRepository;
import com.tony.tang.note.cache.CacheModule;
import com.tony_tang.android.demo.common.scope.ApplicationScope;

import dagger.Binds;
import dagger.Module;

@Module(includes = {CacheModule.class})
public abstract class DataLocalModule {

    @Binds
    @ApplicationScope
    abstract TokenRepository bindTokenRepository(TokenDataRepository tokenDataRepository);


}
