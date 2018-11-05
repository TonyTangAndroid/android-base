package com.tony.tang.note.app;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class NoteCloudDataStore implements NoteDataStore {

    private final NoteRemote remote;
    private final NoteDiskCacheImpl noteDiskCacheImpl;

    @Inject
    public NoteCloudDataStore(NoteRemote remote,
                              NoteDiskCacheImpl noteDiskCacheImpl) {
        this.remote = remote;
        this.noteDiskCacheImpl = noteDiskCacheImpl;
    }

    @Override
    public Single<List<NoteEntity>> list(String keyword) {
        return this.remote.list(keyword)
                .doOnSuccess(list -> cache(keyword, list));
    }

    private void cache(String keyword, List<NoteEntity> list) {
        noteDiskCacheImpl.put(keyword, list);
    }
}