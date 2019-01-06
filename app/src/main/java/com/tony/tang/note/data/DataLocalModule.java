package com.tony.tang.note.data;

import com.tony.tang.note.cache.CacheModule;
import com.tony.tang.note.domain.repository.TokenRepository;

import dagger.Binds;
import dagger.Module;
import dagger.Reusable;

@Module(includes = {CacheModule.class})
public abstract class DataLocalModule {

    @Binds
    @Reusable
    abstract TokenRepository bindTokenRepository(TokenDataRepository tokenDataRepository);


}
