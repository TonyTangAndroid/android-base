package com.jordifierro.androidbase.data.repository;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = NoteBean.TABLE_NAME_NOTE_CACHE)
public class NoteBean {
    public static final String TABLE_NAME_NOTE_CACHE = "note";

    public static final String ID = "id";
    public static final String OBJECT_ID = "objectId";
    public static final String CONTENT = "content";
    public static final String CREATED_AT = "createAt";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    @NonNull
    public int id;


    @ColumnInfo(name = OBJECT_ID)
    @NonNull
    public final String objectId;

    @ColumnInfo(name = CONTENT)
    public final String content;

    @ColumnInfo(name = CREATED_AT)
    public final long createdAt;


    public NoteBean(@NonNull String objectId, String content, long createdAt) {
        this.objectId = objectId;
        this.content = content;
        this.createdAt = createdAt;
    }

}