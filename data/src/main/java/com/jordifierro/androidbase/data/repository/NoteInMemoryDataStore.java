package com.jordifierro.androidbase.data.repository;

import com.jordifierro.androidbase.domain.entity.NoteEntity;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public class NoteInMemoryDataStore implements BadgeDataStore {

    private final NoteInMemoryImpl noteInMemoryImpl;

    @Inject
    public NoteInMemoryDataStore(NoteInMemoryImpl noteInMemoryImpl) {
        this.noteInMemoryImpl = noteInMemoryImpl;
    }

    @Override
    public Single<NoteEntity> getNoteEntity(String noteObjectId) {
        NoteEntity item = noteInMemoryImpl.get(noteObjectId);
        return item == null ? Single.error(new RuntimeException("No cache existed")) : Single.just(item);
    }
}