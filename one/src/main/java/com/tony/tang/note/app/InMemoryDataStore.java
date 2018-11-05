package com.tony.tang.note.app;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public class InMemoryDataStore implements NoteDataStore {

    private final InMemoryImpl noteInMemoryImpl;

    @Inject
    public InMemoryDataStore(InMemoryImpl noteInMemoryImpl) {
        this.noteInMemoryImpl = noteInMemoryImpl;
    }

    @Override
    public Single<List<NoteEntity>> list(String noteObjectId) {
        List<NoteEntity> item = noteInMemoryImpl.get(noteObjectId);
        return item == null ? Single.error(new RuntimeException("No cache existed")) : Single.just(item);
    }
}