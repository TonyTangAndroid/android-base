package com.tony.tang.note.app;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class NoteDataRepository implements NoteRepository {

    private final NoteDataStoreFactory factory;
    private final NoteDiskCacheImpl diskCache;


    @Inject
    public NoteDataRepository(NoteDataStoreFactory factory,
                              NoteDiskCacheImpl diskCache) {
        this.factory = factory;
        this.diskCache = diskCache;
    }

    @Override
    public Single<List<NoteEntity>> list(String noteObjectId) {
        return this.factory.getDataStore(noteObjectId).list(noteObjectId);
    }

    @Override
    public Completable delete(long id) {
        return diskCache.delete(id);
    }

}
