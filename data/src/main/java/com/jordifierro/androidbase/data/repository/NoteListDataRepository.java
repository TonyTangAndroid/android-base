package com.jordifierro.androidbase.data.repository;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.repository.NoteListRepository;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;

public class NoteListDataRepository implements NoteListRepository {

    private final NoteListRemote remote;
    private final NoteDiskCacheImpl diskDataStore;

    @Inject
    public NoteListDataRepository(NoteListRemote remote, NoteDiskCacheImpl diskDataStore) {
        this.remote = remote;
        this.diskDataStore = diskDataStore;
    }

    public Single<List<NoteEntity>> getNotes(Map<String, Object> queryParam) {
        return remote.getNotes(queryParam).doOnSuccess(this::cache);
    }

    private void cache(List<NoteEntity> list) {
        diskDataStore.put(list);
    }
}
