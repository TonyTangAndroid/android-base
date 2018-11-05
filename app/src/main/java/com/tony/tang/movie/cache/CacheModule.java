package com.tony.tang.movie.cache;

import com.tony.tang.movie.AppScope;
import com.tony.tang.movie.data.MovieEntityCache;
import com.tony.tang.movie.db.DbModule;

import dagger.Binds;
import dagger.Module;

@Module(includes = DbModule.class)
public abstract class CacheModule {

    @Binds
    @AppScope
    abstract MovieEntityCache bindNoteEntityDao(MovieEntityCacheImpl noteEntityDao);

}
