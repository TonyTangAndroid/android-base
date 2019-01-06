package com.tony.tang.note.db;

import android.content.Context;

import com.tony.tang.note.app.AppScope;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import dagger.Reusable;

@Module
public abstract class DbModule {

    @Provides
    @AppScope
    static NoteRoomDatabase provideNoteRoomDatabase(Context context) {
        return Room.databaseBuilder(context, NoteRoomDatabase.class, "note_bean.db").build();
    }

    @Provides
    @Reusable
    static NoteBeanDao provideNoteBeanDao(NoteRoomDatabase database) {
        return database.noteBeanDao();
    }
}
