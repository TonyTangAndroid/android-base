package com.tony.tang.movie.data;


import com.tony.tang.movie.app.AppConfig;
import com.tony.tang.movie.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class InMemoryModule {

    @Provides
    @AppScope
    static InMemoryImpl provideNoteInMemoryImpl(AppConfig appConfig) {
        return new InMemoryImpl(appConfig.inMemoryTtl());
    }

}
