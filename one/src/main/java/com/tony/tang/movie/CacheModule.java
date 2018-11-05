package com.tony.tang.movie;

import dagger.Binds;
import dagger.Module;

@Module(includes = DbModule.class)
public abstract class CacheModule {

    @Binds
    @AppScope
    abstract MovieEntityCache bindNoteEntityDao(MovieEntityCacheImpl noteEntityDao);

}
