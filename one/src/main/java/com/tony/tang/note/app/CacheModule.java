package com.tony.tang.note.app;

import dagger.Binds;
import dagger.Module;

@Module(includes = DbModule.class)
public abstract class CacheModule {

    @Binds
    @AppScope
    abstract NoteEntityCache bindNoteEntityDao(NoteEntityCacheImpl noteEntityDao);

}
