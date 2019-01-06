package com.tony.tang.note.cache;

import com.tony.tang.note.data.NoteEntityCache;
import com.tony.tang.note.data.TokenCache;
import com.tony.tang.note.db.DbModule;

import dagger.Binds;
import dagger.Module;
import dagger.Reusable;

@Module(includes = DbModule.class)
public abstract class CacheModule {

    @Binds
    @Reusable
    abstract NoteEntityCache bindNoteEntityDao(NoteEntityCacheImpl noteEntityDao);

    @Binds
    @Reusable
    abstract TokenCache bindUserCache(TokenCacheImpl userCache);

}
