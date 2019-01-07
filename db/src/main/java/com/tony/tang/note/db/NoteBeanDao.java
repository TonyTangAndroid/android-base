package com.tony.tang.note.db;

import java.util.List;

import javax.annotation.Nullable;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

import static com.tony.tang.note.db.NoteBean.OBJECT_ID;
import static com.tony.tang.note.db.NoteBean.TABLE_NAME_NOTE_CACHE;

@Dao
public interface NoteBeanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NoteBean noteBean);

    @Query("DELETE FROM " + TABLE_NAME_NOTE_CACHE + " WHERE " + OBJECT_ID + " = :objectId")
    void delete(String objectId);


    @Query("SELECT * FROM " + TABLE_NAME_NOTE_CACHE + " WHERE " + OBJECT_ID + " = :objectId limit 1")
    @Nullable
    NoteBean get(String objectId);


    @Query("SELECT count(1) FROM " + TABLE_NAME_NOTE_CACHE + " WHERE " + OBJECT_ID + " = :objectId limit 1")
    int count(String objectId);


    @Query("SELECT * FROM " + TABLE_NAME_NOTE_CACHE)
    List<NoteBean> list();

    @Query(value = "DELETE FROM " + TABLE_NAME_NOTE_CACHE)
    void clear();

    /**
     * Room knows how to return a LivePagedListProvider, from which we can get a LiveData and serve
     * it back to UI via ViewModel.
     */
    @Query("SELECT * FROM note ORDER BY createAt COLLATE NOCASE ASC")
    DataSource.Factory<Integer, NoteBean> allNoteBean();

    @Query("SELECT objectId FROM note ORDER BY createAt COLLATE NOCASE ASC")
    Single<List<String>> listObjectId();
}
