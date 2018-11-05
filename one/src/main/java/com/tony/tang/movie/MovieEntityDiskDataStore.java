package com.tony.tang.movie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class MovieEntityDiskDataStore implements MovieEntityDataStore {

    private final MovieEntityDiskCacheImpl noteDiskCache;

    @Inject
    public MovieEntityDiskDataStore(MovieEntityDiskCacheImpl noteDiskCache) {
        this.noteDiskCache = noteDiskCache;
    }

    @Override
    public Single<List<MovieEntity>> list(String keyword) {
        return Single.fromCallable(() -> get(keyword));
    }

    private List<MovieEntity> get(String keyword) {
        return noteDiskCache.get(keyword);
    }

}