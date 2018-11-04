package com.tony.tang.note.domain.repository;

import com.tony.tang.note.domain.entity.NoteEntity;

import io.reactivex.Completable;
import io.reactivex.Single;


public interface NoteRepository {


    Single<String> createNote(NoteEntity note);

    Single<NoteEntity> getNote(String noteObjectId);

    Completable updateNote(NoteEntity note);

    Completable deleteNote(String noteObjectId);
}
