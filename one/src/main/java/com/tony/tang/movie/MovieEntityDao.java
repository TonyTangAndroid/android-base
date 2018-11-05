package com.tony.tang.movie;

import java.util.List;

import javax.annotation.Nullable;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import static com.tony.tang.movie.MovieEntity.OBJECT_ID;
import static com.tony.tang.movie.MovieEntity.TABLE_NAME_NOTE_CACHE;


@Dao
public interface MovieEntityDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<MovieEntity> noteEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieEntity noteEntity);

    @Query("DELETE FROM " + TABLE_NAME_NOTE_CACHE + " WHERE " + OBJECT_ID + " = :id")
    void delete(long id);


    @Query("SELECT * FROM " + TABLE_NAME_NOTE_CACHE + " WHERE " + OBJECT_ID + " = :id limit 1")
    @Nullable
    MovieEntity get(long id);

    @Query("SELECT * FROM " + TABLE_NAME_NOTE_CACHE + " WHERE " + OBJECT_ID + " LIKE '%' || :keyword || '%'")
    List<MovieEntity> list(String keyword);

    @Query("SELECT count(*) FROM " + TABLE_NAME_NOTE_CACHE + " WHERE " + OBJECT_ID + " LIKE '%' || :keyword || '%'")
    int count(String keyword);

    @Query("SELECT * FROM " + TABLE_NAME_NOTE_CACHE)
    List<MovieEntity> list();

    @Query(value = "DELETE FROM " + TABLE_NAME_NOTE_CACHE)
    void clear();


    @Query("SELECT * FROM " + TABLE_NAME_NOTE_CACHE)
    DataSource.Factory<Integer, MovieEntity> allNoteBean();


}
