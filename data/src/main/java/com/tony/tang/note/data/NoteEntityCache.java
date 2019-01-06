package com.tony.tang.note.data;


import com.tony.tang.note.domain.entity.NoteEntity;

import javax.annotation.Nonnull;


public interface NoteEntityCache {

    void delete(String objectId);

    void insertOrReplace(@Nonnull NoteEntity noteEntity);

    boolean isExist(String objectId);

}