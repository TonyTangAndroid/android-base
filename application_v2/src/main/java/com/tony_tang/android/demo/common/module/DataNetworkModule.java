package com.tony_tang.android.demo.common.module;

import com.jordifierro.androidbase.data.repository.NoteDataRepository;
import com.jordifierro.androidbase.data.repository.UserDataRepositoryV2;
import com.jordifierro.androidbase.domain.repository.NoteRepository;
import com.jordifierro.androidbase.domain.repository.UserRepository;
import com.tony_tang.android.demo.common.scope.ApplicationScope;

import dagger.Binds;
import dagger.Module;

@Module(includes = {NetworkModule.class})
public abstract class DataNetworkModule {
    
    @Binds
    //@ApplicationScope
    abstract UserRepository provideUserRepository
            (UserDataRepositoryV2 userDataRepository);

    @Binds
    //@ApplicationScope
    abstract NoteRepository provideNoteRepository(NoteDataRepository noteDataRepository);

}
