package com.tony.tang.movie;


import dagger.Module;
import dagger.Provides;

@Module
public abstract class InMemoryModule {

    @Provides
    @AppScope
    static InMemoryImpl provideNoteInMemoryImpl() {
        return new InMemoryImpl(5 * 1000);
    }

}
