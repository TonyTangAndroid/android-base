package com.tony_tang.android.demo.common.module;

import com.jordifierro.androidbase.data.RestApi;
import com.jordifierro.androidbase.data.repository.NoteListRemote;
import com.jordifierro.androidbase.data.repository.NoteRemote;
import com.jordifierro.androidbase.data.NoteRemoteImpl;
import com.jordifierro.androidbase.data.UserRemoteRepository;
import com.jordifierro.androidbase.domain.repository.UserRepository;
import com.tony_tang.android.demo.common.scope.ApplicationScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {NetworkModule.class})
public abstract class DataRemoteModule {

    @Provides
    @ApplicationScope
    static NoteRemoteImpl provideNoteRemoteImpl(RestApi restApi) {
        return new NoteRemoteImpl(restApi);
    }

    @Binds
    @ApplicationScope
    abstract UserRepository bindUserRepository(UserRemoteRepository userDataRepository);

    @Binds
    @ApplicationScope
    abstract NoteRemote bindNoteRemoteImpl(NoteRemoteImpl userDataRepository);

    @Binds
    @ApplicationScope
    abstract NoteListRemote bindNoteListRemoteImpl(NoteRemoteImpl userDataRepository);

}
