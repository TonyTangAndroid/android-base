package com.jordifierro.androidbase.data.repository;

import com.jordifierro.androidbase.domain.entity.NoteEntity;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface NoteRemote {

    Single<NoteEntity> getNote(String noteObjectId);

    Single<String> createNote(NoteEntity note);

    Completable updateNote(NoteEntity note);

    Completable deleteNote(String noteObjectId);
}