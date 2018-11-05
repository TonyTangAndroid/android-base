package com.tony.tang.movie.app;

import android.app.Application;

import com.tony.tang.movie.AppScope;
import com.tony.tang.movie.data.DataLocalModule;
import com.tony.tang.movie.data.InMemoryModule;
import com.tony.tang.movie.db.DbModule;
import com.tony.tang.movie.db.MovieEntityRoomDatabase;
import com.tony.tang.movie.domain.MovieEntityRepository;
import com.tony.tang.movie.domain.PostThread;
import com.tony.tang.movie.domain.ThreadExecutor;
import com.tony.tang.movie.remote.DataRemoteModule;

import dagger.BindsInstance;
import dagger.Component;

@AppScope
@Component(modules = {
        AppModule.class,
        PrefModule.class,
        DbModule.class,
        InMemoryModule.class,
        ThreadModule.class,
        DataLocalModule.class,
        DataRemoteModule.class})
public interface AppComponent {

    void inject(App app);

    MovieEntityRoomDatabase database();

    MovieEntityRepository noteRepository();

    ThreadExecutor threadExecutor();

    PostThread UIThread();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder appConfig(AppConfig appConfig);

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
