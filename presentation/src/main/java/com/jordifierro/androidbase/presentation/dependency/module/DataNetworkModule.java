package com.jordifierro.androidbase.presentation.dependency.module;

import com.jordifierro.androidbase.data.net.RestApi;
import com.jordifierro.androidbase.data.repository.NoteDataRepository;
import com.jordifierro.androidbase.data.repository.UserDataRepository;
import com.jordifierro.androidbase.domain.repository.NoteRepository;
import com.jordifierro.androidbase.domain.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {NetworkModule.class})
public class DataNetworkModule {


    @Provides
    @Singleton
    UserRepository provideUserRepository(RestApi restApi) {
        return new UserDataRepository(restApi);
    }

    @Provides
    @Singleton
    NoteRepository provideNoteRepository(RestApi restApi) {
        return new NoteDataRepository(restApi);
    }

}