package com.tony.tang.note.app;

import android.content.Context;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class DbModule {

    @Provides
    @AppScope
    static NoteRoomDatabase provideNoteRoomDatabase(Context context) {
        return Room.databaseBuilder(context, NoteRoomDatabase.class, "note_bean.db").build();
    }

    @Provides
    @AppScope
    static NoteEntityDao provideNoteBeanDao(NoteRoomDatabase database) {
        return database.noteBeanDao();
    }
}
