package com.tony.tang.note.data;

import com.tony.tang.note.domain.entity.NoteEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;

public interface NoteListRemote {
    Single<List<NoteEntity>> getNotes(Map<String, Object> queryParam);
}