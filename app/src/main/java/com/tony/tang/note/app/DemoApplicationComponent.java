package com.tony.tang.note.app;

import android.app.Application;

import com.tony.tang.note.data.DataRemoteModule;
import com.tony.tang.note.db.NoteRoomDatabase;
import com.tony.tang.note.domain.executor.ThreadExecutor;
import com.tony.tang.note.domain.executor.UIThread;
import com.tony.tang.note.domain.interactor.note.CreateNoteUseCase;
import com.tony.tang.note.domain.interactor.note.DeleteNoteUseCase;
import com.tony.tang.note.domain.repository.MovieRepository;
import com.tony.tang.note.remote.GsonModule;
import com.tony.tang.note.remote.MovieRemoteModule;

import javax.inject.Named;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@ApplicationScope
@Component(modules = {
        AndroidInjectionModule.class,
        DemoApplicationModule.class,
        PrefModule.class,
        InMemoryRepoModule.class,
        ThreadModule.class,
        GsonModule.class,
        DataRemoteModule.class,
        MovieRemoteModule.class,
        DemoFragmentInjector.class})
public interface DemoApplicationComponent {

    void inject(DemoApplication app);

    NoteRoomDatabase database();

    CreateNoteUseCase createNoteUseCase();

    DeleteNoteUseCase deleteNoteUseCase();


    MovieRepository movieRepository();

    ThreadExecutor threadExecutor();

    UIThread UIThread();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder apiKey(@Named("movie_api_key") String api_key);

        @BindsInstance
        Builder serverUrl(@Named("movie_server_url") String api_key);

        @BindsInstance
        Builder application(Application application);

        DemoApplicationComponent build();
    }
}
