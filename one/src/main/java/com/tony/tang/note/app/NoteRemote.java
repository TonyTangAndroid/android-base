package com.tony.tang.note.app;

import java.util.List;

import io.reactivex.Single;

public interface NoteRemote {

    Single<List<NoteEntity>> list(String keyword);

}
