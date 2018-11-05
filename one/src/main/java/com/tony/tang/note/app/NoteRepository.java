package com.tony.tang.note.app;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;


public interface NoteRepository {

    Single<List<NoteEntity>> list(String keyword);

    Completable delete(long id);
}
