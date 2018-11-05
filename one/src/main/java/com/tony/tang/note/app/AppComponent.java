package com.tony.tang.note.app;

import android.app.Application;

import javax.inject.Named;

import dagger.BindsInstance;
import dagger.Component;

@AppScope
@Component(modules = {
        AppModule.class,
        PrefModule.class,
        DbModule.class,
        InMemoryModule.class,
        ThreadModule.class,
        GsonModule.class,
        DataRemoteModule.class})
public interface AppComponent {

    void inject(App app);

    NoteRoomDatabase database();

    NoteRepository noteRepository();

    NoteRemote movieRepository();

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

        AppComponent build();
    }
}
