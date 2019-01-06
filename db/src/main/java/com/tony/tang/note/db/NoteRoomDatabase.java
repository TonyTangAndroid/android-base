package com.tony.tang.note.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {NoteBean.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class NoteRoomDatabase extends RoomDatabase {
    public abstract NoteBeanDao noteBeanDao();

}