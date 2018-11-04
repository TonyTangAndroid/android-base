package com.tony.tang.note.domain.repository;

import com.tony.tang.note.domain.entity.NoteEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;


public interface NoteListRepository {

    Single<List<NoteEntity>> getNotes(Map<String, Object> queryParam);

}
