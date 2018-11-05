package com.tony.tang.movie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class MovieEntityDataRepository implements MovieEntityRepository {

    private final MovieEntityDataStoreFactory factory;
    private final MovieEntityDiskCacheImpl diskCache;


    @Inject
    public MovieEntityDataRepository(MovieEntityDataStoreFactory factory,
                                     MovieEntityDiskCacheImpl diskCache) {
        this.factory = factory;
        this.diskCache = diskCache;
    }

    @Override
    public Single<List<MovieEntity>> list(String noteObjectId) {
        return this.factory.getDataStore(noteObjectId).list(noteObjectId);
    }

    @Override
    public Completable delete(long id) {
        return diskCache.delete(id);
    }

}