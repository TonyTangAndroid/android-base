package com.tony.tang.note.data;

import com.tony.tang.note.domain.repository.TokenRepository;
import com.tony.tang.note.cache.CacheModule;
import com.tony.tang.note.app.ApplicationScope;

import dagger.Binds;
import dagger.Module;

@Module(includes = {CacheModule.class})
public abstract class DataLocalModule {

    @Binds
    @ApplicationScope
    abstract TokenRepository bindTokenRepository(TokenDataRepository tokenDataRepository);


}
