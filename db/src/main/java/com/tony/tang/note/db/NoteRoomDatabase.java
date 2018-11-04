package com.tony.tang.note.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {NoteBean.class}, version = 1, exportSchema = false)
public abstract class NoteRoomDatabase extends RoomDatabase {
    public abstract NoteBeanDao noteBeanDao();

}
