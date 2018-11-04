package com.tony.tang.note.data;

import com.jordifierro.androidbase.domain.repository.NoteRepository;
import com.tony.tang.note.cache.CacheModule;
import com.tony.tang.note.remote.RemoteModule;
import com.tony_tang.android.demo.common.scope.ApplicationScope;

import dagger.Binds;
import dagger.Module;

@Module(includes = {CacheModule.class, RemoteModule.class})
public abstract class DataRemoteModule {

    @Binds
    @ApplicationScope
    abstract NoteRepository provideNoteRepository(NoteDataRepository noteDataRepository);

}
