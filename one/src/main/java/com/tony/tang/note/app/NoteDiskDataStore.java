package com.tony.tang.note.app;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class NoteDiskDataStore implements NoteDataStore {

    private final NoteDiskCacheImpl noteDiskCache;

    @Inject
    public NoteDiskDataStore(NoteDiskCacheImpl noteDiskCache) {
        this.noteDiskCache = noteDiskCache;
    }

    @Override
    public Single<List<NoteEntity>> list(String keyword) {
        return Single.fromCallable(() -> get(keyword));
    }

    private List<NoteEntity> get(String keyword) {
        return noteDiskCache.get(keyword);
    }

}