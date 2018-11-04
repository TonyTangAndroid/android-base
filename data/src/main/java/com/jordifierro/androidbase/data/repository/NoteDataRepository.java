package com.jordifierro.androidbase.data.repository;

import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.repository.NoteRepository;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class NoteDataRepository implements NoteRepository {

    private final NoteRemote remote;
    private final NoteDataStoreFactory factory;
    private final NoteDiskDataStore diskDataStore;

    @Inject
    public NoteDataRepository(NoteRemote remote, NoteDiskDataStore diskDataStore, NoteDataStoreFactory factory) {
        this.remote = remote;
        this.factory = factory;
        this.diskDataStore = diskDataStore;
    }

    @Override
    public Single<String> createNote(final NoteEntity note) {
        return this.remote.createNote(note);
    }

    @Override
    public Single<NoteEntity> getNote(String noteObjectId) {
        return this.factory.getDataStore(noteObjectId).getNoteEntity(noteObjectId);
    }

    @Override
    public Completable updateNote(NoteEntity note) {
        return this.remote.updateNote(note);
    }

    @Override
    public Completable deleteNote(String noteObjectId) {
        return this.remote.deleteNote(noteObjectId).doOnComplete(() -> clearCache(noteObjectId));
    }

    private void clearCache(String noteObjectId) {
        diskDataStore.delete(noteObjectId);
    }

}
