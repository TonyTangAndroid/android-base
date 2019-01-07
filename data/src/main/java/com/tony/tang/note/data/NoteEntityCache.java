package com.tony.tang.note.data;


import com.tony.tang.note.domain.entity.NoteEntity;

import java.util.List;

import javax.annotation.Nonnull;

import io.reactivex.Single;


public interface NoteEntityCache {

    void delete(String objectId);

    void insertOrReplace(@Nonnull NoteEntity noteEntity);

    boolean isExist(String objectId);

    Single<List<String>> listObjectId();
}