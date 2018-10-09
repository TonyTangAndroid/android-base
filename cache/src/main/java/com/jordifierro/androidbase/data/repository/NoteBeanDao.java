package com.jordifierro.androidbase.data.repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import javax.annotation.Nullable;

import static com.jordifierro.androidbase.data.repository.NoteBean.OBJECT_ID;
import static com.jordifierro.androidbase.data.repository.NoteBean.TABLE_NAME_NOTE_CACHE;

@Dao
public interface NoteBeanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NoteBean noteBean);

    @Query("DELETE FROM " + TABLE_NAME_NOTE_CACHE + " WHERE " + OBJECT_ID + " = :objectId")
    void delete(String objectId);


    @Query("SELECT * FROM " + TABLE_NAME_NOTE_CACHE + " WHERE " + OBJECT_ID + " = :objectId limit 1")
    @Nullable
    NoteBean get(String objectId);

    @Query("SELECT * FROM " + TABLE_NAME_NOTE_CACHE)
    List<NoteBean> list();

    @Query(value = "DELETE FROM " + TABLE_NAME_NOTE_CACHE)
    void clear();
}
