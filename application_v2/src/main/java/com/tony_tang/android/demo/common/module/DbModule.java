package com.tony_tang.android.demo.common.module;

import androidx.room.Room;
import android.content.Context;

import com.jordifierro.androidbase.data.repository.NoteBeanDao;
import com.jordifierro.androidbase.data.repository.NoteEntityDaoImpl;
import com.jordifierro.androidbase.data.repository.NoteRoomDatabase;
import com.jordifierro.androidbase.domain.cache.NoteEntityDao;
import com.tony_tang.android.demo.common.scope.ApplicationScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import hugo.weaving.DebugLog;

@DebugLog
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

    @Binds
    @ApplicationScope
    abstract NoteEntityDao bindNoteEntityDao(NoteEntityDaoImpl noteEntityDao);


}
