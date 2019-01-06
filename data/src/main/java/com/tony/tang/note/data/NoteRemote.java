package com.tony.tang.note.data;

import com.tony.tang.note.domain.entity.NoteData;
import com.tony.tang.note.domain.entity.NoteEntity;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface NoteRemote {

    Single<NoteEntity> getNote(String noteObjectId);

    Single<String> createNote(NoteData note);

    Completable updateNote(NoteData note);

    Completable deleteNote(String noteObjectId);
}