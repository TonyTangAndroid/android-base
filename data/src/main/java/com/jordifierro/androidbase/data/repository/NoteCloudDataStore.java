package com.jordifierro.androidbase.data.repository;

import com.jordifierro.androidbase.data.net.RestApi;
import com.jordifierro.androidbase.domain.entity.NoteEntitiesWrapper;
import com.jordifierro.androidbase.domain.entity.NoteEntity;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public class NoteCloudDataStore implements BadgeDataStore {

    private final RestApi restApi;
    private final NoteDiskCacheImpl noteDiskCacheImpl;

    @Inject
    public NoteCloudDataStore(RestApi restApi,
                              NoteDiskCacheImpl noteDiskCacheImpl) {
        this.restApi = restApi;
        this.noteDiskCacheImpl = noteDiskCacheImpl;
    }


    @Override
    public Single<NoteEntity> getNoteEntity(String noteObjectId) {
        return this.restApi.getNote(noteObjectId)
                .flatMap(Validator::validate)
                .doOnSuccess(this::cache);
    }

    private void cache(NoteEntity noteEntity) {
        noteDiskCacheImpl.put(noteEntity);
    }


    public Single<List<NoteEntity>> getNotes(Map<String, Object> queryParam) {
        return restApi.getNotes(null, queryParam)
                .flatMap(Validator::validate).map(NoteEntitiesWrapper::getResults).doOnSuccess(this::cache);
    }

    private void cache(List<NoteEntity> list) {
        noteDiskCacheImpl.put(list);
    }
}