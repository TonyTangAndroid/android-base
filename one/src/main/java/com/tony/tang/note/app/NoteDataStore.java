package com.tony.tang.note.app;

import java.util.List;

import io.reactivex.Single;

public interface NoteDataStore {
    Single<List<NoteEntity>> list(String keyword);
}