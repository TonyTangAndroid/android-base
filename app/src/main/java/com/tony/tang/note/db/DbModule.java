package com.tony.tang.note.db;

import android.content.Context;

import com.tony.tang.note.app.ApplicationScope;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class DbModule {

    @Provides
    @ApplicationScope
    static NoteRoomDatabase provideNoteRoomDatabase(Context context) {
        return Room.databaseBuilder(context, NoteRoomDatabase.class, "note_bean.db").build();
    }

    @Provides
    @ApplicationScope
    static NoteBeanDao provideNoteBeanDao(NoteRoomDatabase database) {
        return database.noteBeanDao();
    }
}
