package com.tony.tang.note.data;


import com.jordifierro.androidbase.domain.entity.NoteEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public interface NoteEntityCache {

    @Nullable
    NoteEntity find(String objectId);

    void delete(String objectId);

    void insertOrReplace(@Nonnull NoteEntity noteEntity);

    boolean isExist(String objectId);

    boolean isExpired(String objectId);
}