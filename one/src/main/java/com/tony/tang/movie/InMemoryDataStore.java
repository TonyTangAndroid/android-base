package com.tony.tang.movie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public class InMemoryDataStore implements MovieEntityDataStore {

    private final InMemoryImpl noteInMemoryImpl;

    @Inject
    public InMemoryDataStore(InMemoryImpl noteInMemoryImpl) {
        this.noteInMemoryImpl = noteInMemoryImpl;
    }

    @Override
    public Single<List<MovieEntity>> list(String noteObjectId) {
        List<MovieEntity> item = noteInMemoryImpl.get(noteObjectId);
        return item == null ? Single.error(new RuntimeException("No cache existed")) : Single.just(item);
    }
}