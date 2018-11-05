package com.tony.tang.movie.data;

import com.tony.tang.movie.AppScope;
import com.tony.tang.movie.cache.CacheModule;
import com.tony.tang.movie.domain.MovieEntityRepository;
import com.tony.tang.movie.remote.DataRemoteModule;

import dagger.Binds;
import dagger.Module;

@Module(includes = {CacheModule.class, DataRemoteModule.class})
public abstract class DataLocalModule {

    @Binds
    @AppScope
    abstract MovieEntityRepository provideNoteRepository(MovieEntityDataRepository noteDataRepository);
}
