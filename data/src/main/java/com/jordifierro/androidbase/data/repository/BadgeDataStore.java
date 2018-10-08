package com.jordifierro.androidbase.data.repository;

import com.jordifierro.androidbase.domain.entity.NoteEntity;

import io.reactivex.Single;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public interface BadgeDataStore {
    Single<NoteEntity> getNoteEntity(String noteObjectId);
}