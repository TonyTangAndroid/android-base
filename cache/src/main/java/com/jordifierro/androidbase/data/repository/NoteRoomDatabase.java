package com.jordifierro.androidbase.data.repository;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {NoteBean.class}, version = 1,exportSchema = false)
public abstract class NoteRoomDatabase extends RoomDatabase {
    public abstract NoteBeanDao noteBeanDao();

}
