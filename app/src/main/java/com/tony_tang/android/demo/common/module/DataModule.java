package com.tony_tang.android.demo.common.module;

import com.tony.tang.note.remote.DataRemoteModule;
import com.jordifierro.androidbase.data.repository.NoteDataRepository;
import com.jordifierro.androidbase.domain.repository.NoteRepository;
import com.tony_tang.android.demo.common.scope.ApplicationScope;

import dagger.Binds;
import dagger.Module;

@Module(includes = {DataLocalModule.class, DataRemoteModule.class})
public abstract class DataModule {

    @Binds
    @ApplicationScope
    abstract NoteRepository provideNoteRepository(NoteDataRepository noteDataRepository);


}
