package com.tony_tang.android.demo.common.module;

import com.jordifierro.androidbase.data.net.RestApi;
import com.jordifierro.androidbase.data.repository.NoteDataRepository;
import com.jordifierro.androidbase.data.repository.UserDataRepository;
import com.jordifierro.androidbase.domain.repository.NoteRepository;
import com.jordifierro.androidbase.domain.repository.UserRepository;
import com.tony_tang.android.demo.common.scope.ApplicationScope;

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
