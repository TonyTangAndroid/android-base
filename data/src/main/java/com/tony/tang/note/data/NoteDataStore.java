package com.tony.tang.note.data;

import com.tony.tang.note.domain.entity.NoteEntity;

import io.reactivex.Single;

public interface NoteDataStore {
    Single<NoteEntity> getNoteEntity(String noteObjectId);
}