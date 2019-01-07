package com.tony.tang.note.data;

import com.tony.tang.note.domain.entity.NoteData;
import com.tony.tang.note.domain.entity.NoteEntity;
import com.tony.tang.note.domain.repository.NoteRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleSource;

public class NoteDataRepository implements NoteRepository {

    private final NoteRemote remote;
    private final NoteEntityCache noteEntityDao;
    private final NoteDiskCacheImpl noteDiskCacheImpl;

    @Inject
    public NoteDataRepository(NoteRemote remote,
                              NoteEntityCache noteEntityDao,
                              NoteDiskCacheImpl noteDiskCacheImpl) {
        this.remote = remote;
        this.noteEntityDao = noteEntityDao;
        this.noteDiskCacheImpl = noteDiskCacheImpl;
    }

    @Override
    public Single<NoteEntity> createNote(final NoteData note) {
        return this.remote.createNote(note).flatMap(this::toEntity);
    }

    private SingleSource<NoteEntity> toEntity(String objectId) {
        return getNote(objectId);
    }

    @Override
    public Single<NoteEntity> getNote(String noteObjectId) {
        return this.remote.getNote(noteObjectId)
                .doOnSuccess(this::cache);
    }

    private void cache(NoteEntity noteEntity) {
        noteDiskCacheImpl.put(noteEntity);
    }

    @Override
    public Single<NoteEntity> updateNote(NoteData note) {
        //XXX when the update note should be executed.
        return this.remote.updateNote(note).andThen(getNote(note.objectId()));
    }

    @Override
    public Single<List<String>> objectIdList() {
        return noteEntityDao.listObjectId();
    }

    @Override
    public Completable deleteNote(String noteObjectId) {
        return this.remote.deleteNote(noteObjectId).doOnComplete(() -> clearCache(noteObjectId));
    }

    private void clearCache(String noteObjectId) {
        noteDiskCacheImpl.delete(noteObjectId);
    }

}
