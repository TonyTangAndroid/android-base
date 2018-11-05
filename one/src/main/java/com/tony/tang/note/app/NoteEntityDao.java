package com.tony.tang.note.app;

import java.util.List;

import javax.annotation.Nullable;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import static com.tony.tang.note.app.NoteEntity.OBJECT_ID;
import static com.tony.tang.note.app.NoteEntity.TABLE_NAME_NOTE_CACHE;


@Dao
public interface NoteEntityDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<NoteEntity> noteEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NoteEntity noteEntity);

    @Query("DELETE FROM " + TABLE_NAME_NOTE_CACHE + " WHERE " + OBJECT_ID + " = :id")
    void delete(long id);


    @Query("SELECT * FROM " + TABLE_NAME_NOTE_CACHE + " WHERE " + OBJECT_ID + " = :id limit 1")
    @Nullable
    NoteEntity get(long id);

    @Query("SELECT * FROM " + TABLE_NAME_NOTE_CACHE + " WHERE " + OBJECT_ID + " LIKE '%' || :keyword || '%'")
    List<NoteEntity> list(String keyword);

    @Query("SELECT count(*) FROM " + TABLE_NAME_NOTE_CACHE + " WHERE " + OBJECT_ID + " LIKE '%' || :keyword || '%'")
    int count(String keyword);

    @Query("SELECT * FROM " + TABLE_NAME_NOTE_CACHE)
    List<NoteEntity> list();

    @Query(value = "DELETE FROM " + TABLE_NAME_NOTE_CACHE)
    void clear();


    @Query("SELECT * FROM " + TABLE_NAME_NOTE_CACHE)
    DataSource.Factory<Integer, NoteEntity> allNoteBean();


}
