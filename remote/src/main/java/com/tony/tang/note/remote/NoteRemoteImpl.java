package com.tony.tang.note.remote;

import com.jordifierro.androidbase.data.repository.NoteListRemote;
import com.jordifierro.androidbase.data.repository.NoteRemote;
import com.jordifierro.androidbase.domain.entity.NoteEntity;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class NoteRemoteImpl implements NoteListRemote, NoteRemote {

    private final RestApi restApi;

    @Inject
    public NoteRemoteImpl(RestApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public Single<String> createNote(final NoteEntity note) {
        return this.restApi.createNote(note)
                .flatMap(Validator::validate).map(CreatedWrapper::getObjectId);
    }

    @Override
    public Single<NoteEntity> getNote(String noteObjectId) {
        return this.restApi.getNote(noteObjectId).flatMap(Validator::validate);
    }


    @Override
    public Single<List<NoteEntity>> getNotes(Map<String, Object> queryParam) {
        return restApi.getNotes(queryParam).flatMap(Validator::validate).map(NoteEntitiesWrapper::results);
    }

    @Override
    public Completable updateNote(NoteEntity note) {
        return this.restApi.updateNote(note.objectId(), note)
                .flatMap(Validator::validate).ignoreElement();
    }

    @Override
    public Completable deleteNote(String noteObjectId) {
        return this.restApi.deleteNote(noteObjectId).flatMap(Validator::validate).ignoreElement();
    }
}
