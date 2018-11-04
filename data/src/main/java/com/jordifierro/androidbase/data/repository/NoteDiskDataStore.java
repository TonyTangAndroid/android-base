package com.jordifierro.androidbase.data.repository;

import com.jordifierro.androidbase.domain.entity.NoteEntity;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public class NoteDiskDataStore implements NoteDataStore {

    private final NoteDiskCacheImpl noteDiskCache;

    @Inject
    public NoteDiskDataStore(NoteDiskCacheImpl noteDiskCache) {
        this.noteDiskCache = noteDiskCache;
    }

    @Override
    public Single<NoteEntity> getNoteEntity(String noteObjectId) {
        return Single.fromCallable(() -> get(noteObjectId));
    }

    private NoteEntity get(String noteObjectId) {
        NoteEntity noteEntity = noteDiskCache.get(noteObjectId);
        if (noteEntity == null) {
            throw new RuntimeException("invalid disk cache");
        } else {
            return noteEntity;
        }
    }

    public void delete(String noteObjectId) {
        noteDiskCache.delete(noteObjectId);
    }
}