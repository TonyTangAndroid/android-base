package com.tony.tang.movie.db;


import com.tony.tang.movie.domain.MovieEntity;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MovieEntity.class}, version = 1, exportSchema = false)
public abstract class MovieEntityRoomDatabase extends RoomDatabase {
    public abstract MovieEntityDao noteBeanDao();

}
