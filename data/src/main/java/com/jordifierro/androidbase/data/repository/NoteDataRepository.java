package com.jordifierro.androidbase.data.repository;

import com.jordifierro.androidbase.data.net.RestApi;
import com.jordifierro.androidbase.domain.entity.CreatedWrapper;
import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.entity.UpdatedWrapper;
import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.entity.VoidEntity;
import com.jordifierro.androidbase.domain.repository.NoteRepository;

import java.util.List;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import io.reactivex.Observable;

public class NoteDataRepository extends RestApiRepository implements NoteRepository {

    private final RestApi restApi;

    @DebugLog
    @Inject
    public NoteDataRepository(RestApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public Observable<CreatedWrapper> createNote(UserEntity user, final NoteEntity note) {
        return this.restApi.createNote(user.getSessionToken(), note)
                .map(noteEntityResponse -> {
                    handleResponseError(noteEntityResponse);
                    return noteEntityResponse.body();
                });
    }

    @Override
    public Observable<NoteEntity> getNote(UserEntity user, String noteObjectId) {
        return this.restApi.getNote(user.getSessionToken(), noteObjectId)
                .map(noteEntityResponse -> {
                    handleResponseError(noteEntityResponse);
                    return noteEntityResponse.body();
                });
    }

    @Override
    public Observable<List<NoteEntity>> getNotes(UserEntity user) {
        return this.restApi.getNotes(user.getSessionToken())
                .map(listResponse -> {
                    handleResponseError(listResponse);
                    return listResponse.body().getResults();
                });
    }

    @Override
    public Observable<UpdatedWrapper> updateNote(UserEntity user, NoteEntity note) {
        return this.restApi.updateNote(user.getSessionToken(), note.getObjectId(), note)
                .map(noteEntityResponse -> {
                    handleResponseError(noteEntityResponse);
                    return noteEntityResponse.body();
                });
    }

    @Override
    public Observable<VoidEntity> deleteNote(UserEntity user, String noteObjectId) {
        return this.restApi.deleteNote(user.getSessionToken(), noteObjectId)
                .map(response -> {
                    handleResponseError(response);
                    return response.body();
                });
    }
}
