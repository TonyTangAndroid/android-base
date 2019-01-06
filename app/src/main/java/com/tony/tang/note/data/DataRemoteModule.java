package com.tony.tang.note.data;

import com.tony.tang.note.cache.CacheModule;
import com.tony.tang.note.domain.repository.NoteRepository;
import com.tony.tang.note.remote.RemoteModule;

import dagger.Binds;
import dagger.Module;
import dagger.Reusable;

@Module(includes = {CacheModule.class, RemoteModule.class})
public abstract class DataRemoteModule {

    @Binds
    @Reusable
    abstract NoteRepository provideNoteRepository(NoteDataRepository noteDataRepository);

}
