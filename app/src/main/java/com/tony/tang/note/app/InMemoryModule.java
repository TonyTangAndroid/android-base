package com.tony.tang.note.app;

import com.tony.tang.note.data.NoteInMemoryImpl;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class InMemoryModule {

    @Provides
    @AppScope
    static NoteInMemoryImpl provideNoteInMemoryImpl() {
        return new NoteInMemoryImpl(5 * 1000);
    }

}
