package com.jordifierro.androidbase.domain.repository;

import com.jordifierro.androidbase.domain.entity.CreatedWrapper;
import com.jordifierro.androidbase.domain.entity.NoteEntity;
import com.jordifierro.androidbase.domain.entity.UpdatedWrapper;
import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.entity.VoidEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


public interface NoteRepository {


    Observable<CreatedWrapper> createNote(UserEntity user, NoteEntity note);

    Observable<NoteEntity> getNote(UserEntity user, String noteObjectId);

    Observable<List<NoteEntity>> getNotes(UserEntity user, Map<String, Object> queryParam);

    Observable<UpdatedWrapper> updateNote(UserEntity user, NoteEntity note);

    Observable<VoidEntity> deleteNote(UserEntity user, String noteObjectId);
}
