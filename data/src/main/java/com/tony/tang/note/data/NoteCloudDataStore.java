package com.tony.tang.note.data;

import com.jordifierro.androidbase.domain.entity.NoteEntity;

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
    public Single<NoteEntity> getNoteEntity(String noteObjectId) {
        return this.remote.getNote(noteObjectId)
                .doOnSuccess(this::cache);
    }

    private void cache(NoteEntity noteEntity) {
        noteDiskCacheImpl.put(noteEntity);
    }
}