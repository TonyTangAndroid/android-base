package com.tony.tang.note.data;

import com.jordifierro.androidbase.domain.entity.NoteEntity;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;

public class NoteListCloudDataStore {

    private final NoteListRemote remote;
    private final NoteDiskCacheImpl noteDiskCacheImpl;

    @Inject
    public NoteListCloudDataStore(NoteListRemote remote,
                                  NoteDiskCacheImpl noteDiskCacheImpl) {
        this.remote = remote;
        this.noteDiskCacheImpl = noteDiskCacheImpl;
    }

    public Single<List<NoteEntity>> getNotes(Map<String, Object> queryParam) {
        return remote.getNotes(queryParam).doOnSuccess(this::cache);
    }

    private void cache(List<NoteEntity> list) {
        noteDiskCacheImpl.put(list);
    }
}