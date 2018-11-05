package com.tony.tang.note.data;

import com.tony.tang.note.domain.repository.NoteRepository;
import com.tony.tang.note.cache.CacheModule;
import com.tony.tang.note.remote.RemoteModule;
import com.tony.tang.note.app.AppScope;

import dagger.Binds;
import dagger.Module;

@Module(includes = {CacheModule.class, RemoteModule.class})
public abstract class DataRemoteModule {

    @Binds
    @AppScope
    abstract NoteRepository provideNoteRepository(NoteDataRepository noteDataRepository);

}
