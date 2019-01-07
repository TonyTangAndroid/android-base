package com.tony.tang.note.domain.repository;

import com.tony.tang.note.domain.entity.NoteData;
import com.tony.tang.note.domain.entity.NoteEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;


public interface NoteRepository {


    Single<NoteEntity> createNote(NoteData note);

    Single<NoteEntity> getNote(String noteObjectId);

    Single<NoteEntity> updateNote(NoteData note);

    Single<List<String>> objectIdList();

    Completable deleteNote(String noteObjectId);
}
