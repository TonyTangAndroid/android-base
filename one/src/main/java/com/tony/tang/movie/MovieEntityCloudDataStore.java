package com.tony.tang.movie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class MovieEntityCloudDataStore implements MovieEntityDataStore {

    private final MovieEntityRemote remote;
    private final MovieEntityDiskCacheImpl noteDiskCacheImpl;

    @Inject
    public MovieEntityCloudDataStore(MovieEntityRemote remote,
                                     MovieEntityDiskCacheImpl noteDiskCacheImpl) {
        this.remote = remote;
        this.noteDiskCacheImpl = noteDiskCacheImpl;
    }

    @Override
    public Single<List<MovieEntity>> list(String keyword) {
        return this.remote.list(keyword)
                .doOnSuccess(list -> cache(keyword, list));
    }

    private void cache(String keyword, List<MovieEntity> list) {
        noteDiskCacheImpl.put(keyword, list);
    }
}