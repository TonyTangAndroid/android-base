package com.jordifierro.androidbase.data.repository;

import com.jordifierro.androidbase.domain.entity.NoteEntity;

import io.reactivex.Single;

public interface NoteDataStore {
    Single<NoteEntity> getNoteEntity(String noteObjectId);
}