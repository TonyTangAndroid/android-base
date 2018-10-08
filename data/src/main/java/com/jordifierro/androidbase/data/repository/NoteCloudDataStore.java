package com.jordifierro.androidbase.data.repository;

import com.jordifierro.androidbase.data.net.RestApi;
import com.jordifierro.androidbase.domain.entity.NoteEntity;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Response;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public class NoteCloudDataStore extends RestApiRepository implements BadgeDataStore {

    private final RestApi restApi;
    private final NoteDiskCacheImpl noteDiskCacheImpl;

    @Inject
    public NoteCloudDataStore(RestApi restApi, NoteDiskCacheImpl noteDiskCacheImpl) {
        this.restApi = restApi;
        this.noteDiskCacheImpl = noteDiskCacheImpl;
    }


    @Override
    public Single<NoteEntity> getNoteEntity(String noteObjectId) {
        return this.restApi.getNote(noteObjectId)
                .map(this::validateResponse)
                .doOnSuccess(this::cache);
    }

    private void cache(NoteEntity noteEntity) {
        noteDiskCacheImpl.put(noteEntity);
    }

    private NoteEntity validateResponse(Response<NoteEntity> noteEntityResponse) {
        handleResponseError(noteEntityResponse);
        return noteEntityResponse.body();
    }

    public Single<List<NoteEntity>> getNotes(Map<String, Object> queryParam) {
        return restApi.getNotes(null, queryParam)
                .map(listResponse -> {
                    handleResponseError(listResponse);
                    return listResponse.body().getResults();
                }).doOnSuccess(this::cache);
    }

    private void cache(List<NoteEntity> list) {

        noteDiskCacheImpl.put(list);

    }
}