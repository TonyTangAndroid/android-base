package com.jordifierro.androidbase.data.repository;

import com.jordifierro.androidbase.data.net.RestApi;
import com.jordifierro.androidbase.domain.entity.CreatedWrapper;
import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.entity.VoidEntity;
import com.jordifierro.androidbase.domain.repository.NoteRepository;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Response;

public class NoteDataRepository extends RestApiRepository implements NoteRepository {

    private final RestApi restApi;
    private final NoteCloudDataStore noteCloudDataStore;
    private final BadgeDataStoreFactory badgeDataStoreFactory;

    @Inject
    public NoteDataRepository(RestApi restApi,
                              NoteCloudDataStore noteCloudDataStore,
                              BadgeDataStoreFactory badgeDataStoreFactory) {
        this.restApi = restApi;
        this.noteCloudDataStore = noteCloudDataStore;
        this.badgeDataStoreFactory = badgeDataStoreFactory;
    }

    @Override
    public Single<String> createNote(final NoteEntity note) {
        return this.restApi.createNote(note)
                .map(this::validResponse)
                .map(CreatedWrapper::getObjectId);
    }

    private CreatedWrapper validResponse(Response<CreatedWrapper> noteEntityResponse) {
        handleResponseError(noteEntityResponse);
        return noteEntityResponse.body();
    }

    @Override
    public Single<NoteEntity> getNote(String noteObjectId) {
        return Single.just(noteObjectId).flatMap(this::get);
    }

    private Single<NoteEntity> get(String noteObjectId) {
        return this.badgeDataStoreFactory.getDataStore(noteObjectId).getNoteEntity(noteObjectId);
    }

    @Override
    public Single<List<NoteEntity>> getNotes(Map<String, Object> queryParam) {
        return noteCloudDataStore.getNotes(queryParam);
    }

    @Override
    public Completable updateNote(NoteEntity note) {
        return this.restApi.updateNote(note.getObjectId(), note)
                .map(noteEntityResponse -> {
                    handleResponseError(noteEntityResponse);
                    return noteEntityResponse.body();
                }).ignoreElement();
    }

    @Override
    public Completable deleteNote(String noteObjectId) {
        return this.restApi.deleteNote(noteObjectId).map(this::validateResponse)
                .ignoreElement().doOnComplete(() -> clearCache(noteObjectId));
    }

    private void clearCache(String noteObjectId) {
        badgeDataStoreFactory.noteDiskDataStore().delete(noteObjectId);
    }

    private VoidEntity validateResponse(Response<VoidEntity> response) {
        handleResponseError(response);
        return response.body();
    }
}
