package com.tony.tang.movie;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MovieEntity.class}, version = 1, exportSchema = false)
public abstract class MovieEntityRoomDatabase extends RoomDatabase {
    public abstract MovieEntityDao noteBeanDao();

}
