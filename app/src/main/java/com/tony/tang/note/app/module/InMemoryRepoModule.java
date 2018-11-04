package com.tony.tang.note.app.module;

import com.tony.tang.note.data.NoteInMemoryImpl;
import com.tony_tang.android.demo.common.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class InMemoryRepoModule {

    @Provides
    @ApplicationScope
    static NoteInMemoryImpl provideNoteInMemoryImpl() {
        return new NoteInMemoryImpl(5 * 1000);
    }

}
