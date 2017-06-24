package com.jordifierro.androidbase.application.dependency.module;

import com.jordifierro.androidbase.application.dependency.ApplicationScope;
import com.jordifierro.androidbase.data.net.RestApi;
import com.jordifierro.androidbase.data.repository.NoteDataRepository;
import com.jordifierro.androidbase.data.repository.UserDataRepository;
import com.jordifierro.androidbase.domain.repository.NoteRepository;
import com.jordifierro.androidbase.domain.repository.UserRepository;

import dagger.Module;
import dagger.Provides;

@Module(includes = {NetworkModule.class})
public class DataNetworkModule {


    @Provides
    @ApplicationScope
    UserRepository provideUserRepository(RestApi restApi) {
        return new UserDataRepository(restApi);
    }

    @Provides
    @ApplicationScope
    NoteRepository provideNoteRepository(RestApi restApi) {
        return new NoteDataRepository(restApi);
    }

}
