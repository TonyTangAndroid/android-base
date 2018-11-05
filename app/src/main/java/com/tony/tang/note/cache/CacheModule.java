package com.tony.tang.note.cache;

import com.tony.tang.note.data.NoteEntityCache;
import com.tony.tang.note.data.TokenCache;
import com.tony.tang.note.db.DbModule;
import com.tony.tang.note.app.AppScope;

import dagger.Binds;
import dagger.Module;

@Module(includes = DbModule.class)
public abstract class CacheModule {

    @Binds
    @AppScope
    abstract NoteEntityCache bindNoteEntityDao(NoteEntityCacheImpl noteEntityDao);

    @Binds
    @AppScope
    abstract TokenCache bindUserCache(TokenCacheImpl userCache);

}
