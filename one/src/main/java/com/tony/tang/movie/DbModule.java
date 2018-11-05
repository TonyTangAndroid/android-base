package com.tony.tang.movie;

import android.content.Context;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class DbModule {

    @Provides
    @AppScope
    static MovieEntityRoomDatabase provideNoteRoomDatabase(Context context) {
        return Room.databaseBuilder(context, MovieEntityRoomDatabase.class, "note_bean.db").build();
    }

    @Provides
    @AppScope
    static MovieEntityDao provideNoteBeanDao(MovieEntityRoomDatabase database) {
        return database.noteBeanDao();
    }
}
