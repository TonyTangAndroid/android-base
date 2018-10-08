package com.jordifierro.androidbase.data.repository;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = NoteBean.TABLE_NAME_NOTE_CACHE)
public class NoteBean {
    public static final String TABLE_NAME_NOTE_CACHE = "note";

    public static final String OBJECT_ID = "objectId";
    public static final String CONTENT = "content";
    public static final String CREATED_AT = "createAt";


    @PrimaryKey
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