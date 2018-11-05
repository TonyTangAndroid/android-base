package com.tony.tang.movie;

import android.app.Application;

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

    MovieEntityRoomDatabase database();

    MovieEntityRepository noteRepository();

    MovieEntityRemote movieRepository();

    ThreadExecutor threadExecutor();

    UIThread UIThread();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder appConfig(AppConfig appConfig);

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
