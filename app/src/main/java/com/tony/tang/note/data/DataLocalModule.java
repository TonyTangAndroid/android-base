package com.tony.tang.note.data;

import com.tony.tang.note.domain.repository.TokenRepository;
import com.tony.tang.note.cache.CacheModule;
import com.tony.tang.note.app.AppScope;

import dagger.Binds;
import dagger.Module;

@Module(includes = {CacheModule.class})
public abstract class DataLocalModule {

    @Binds
    @AppScope
    abstract TokenRepository bindTokenRepository(TokenDataRepository tokenDataRepository);


}
