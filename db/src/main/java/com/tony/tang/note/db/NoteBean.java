package com.tony.tang.note.db;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = NoteBean.TABLE_NAME_NOTE_CACHE)
public class NoteBean {
    public static final String TABLE_NAME_NOTE_CACHE = "note";

    public static final String ID = "id";
    public static final String OBJECT_ID = "objectId";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String STATUS = "status";
    public static final String CREATED_AT = "createAt";
    public static final String UPDATED_AT = "updatedAt";
    @ColumnInfo(name = OBJECT_ID)
    public final String objectId;

    @ColumnInfo(name = TITLE)
    public final String title;


    @ColumnInfo(name = CONTENT)
    public final String content;

    @ColumnInfo(name = STATUS)
    public final int status;

    @ColumnInfo(name = CREATED_AT)
    public final Date createdAt;

    @ColumnInfo(name = UPDATED_AT)
    public final Date updatedAt;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    public int id;


    public NoteBean(String objectId, String title, String content,
                    int status, Date createdAt, Date updatedAt) {
        this.objectId = objectId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }

    @Ignore
    public NoteBean(String objectId, String title, String content, Date createdAt) {
        this(objectId, title, content, 0, createdAt, createdAt);
    }

    @Ignore
    public NoteBean(String title, String content, Date createdAt) {
        this(null, title, content, createdAt);
    }

}