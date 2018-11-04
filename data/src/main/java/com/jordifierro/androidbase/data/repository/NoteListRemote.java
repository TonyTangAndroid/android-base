package com.jordifierro.androidbase.data.repository;

import com.jordifierro.androidbase.domain.entity.NoteEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;

public interface NoteListRemote {
    Single<List<NoteEntity>> getNotes(Map<String, Object> queryParam);
}