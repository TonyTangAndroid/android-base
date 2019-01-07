package com.tony.tang.note.remote;

import com.google.gson.Gson;
import com.tony.tang.note.data.NoteListRemote;
import com.tony.tang.note.data.NoteRemote;
import com.tony.tang.note.domain.entity.NoteData;
import com.tony.tang.note.domain.entity.NoteEntity;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class NoteRemoteImpl implements NoteListRemote, NoteRemote {

    private final Gson gson;
    private final RestApi restApi;

    @Inject
    public NoteRemoteImpl(Gson gson, RestApi restApi) {
        this.gson = gson;
        this.restApi = restApi;
    }

    @Override
    public Single<String> createNote(final NoteData note) {
        return this.restApi.createNote(note)
                .flatMap(Validator::validate).map(CreatedWrapper::getObjectId);
    }

    @Override
    public Single<NoteEntity> getNote(String noteObjectId) {
        return this.restApi.getNote(noteObjectId).flatMap(Validator::validate);
    }


    @Override
    public Single<List<NoteEntity>> getNotes(@Nullable Map<String, Object> whereMap) {
        return restApi.getNotes(10, whereMap == null ? null : gson.toJson(whereMap))
                .flatMap(Validator::validate)
                .map(NoteEntitiesWrapper::results);
    }

    @Override
    public Completable updateNote(NoteData note) {
        return this.restApi.updateNote(note.objectId(), note)
                .flatMap(Validator::validate).ignoreElement();
    }

    @Override
    public Completable deleteNote(String noteObjectId) {
        return this.restApi.deleteNote(noteObjectId).flatMap(Validator::validate).ignoreElement();
    }
}
