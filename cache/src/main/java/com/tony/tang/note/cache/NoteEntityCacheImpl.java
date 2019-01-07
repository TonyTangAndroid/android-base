package com.tony.tang.note.cache;

import com.tony.tang.note.data.NoteEntityCache;
import com.tony.tang.note.db.NoteBean;
import com.tony.tang.note.db.NoteBeanDao;
import com.tony.tang.note.domain.entity.NoteEntity;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.Single;


public class NoteEntityCacheImpl implements NoteEntityCache {

    private final NoteBeanDao noteBeanDao;

    @Inject
    public NoteEntityCacheImpl(NoteBeanDao noteBeanDao) {
        this.noteBeanDao = noteBeanDao;
    }

    @Override
    public void delete(String objectId) {
        noteBeanDao.delete(objectId);
    }

    @Override
    public void insertOrReplace(@Nonnull NoteEntity noteEntity) {
        noteBeanDao.insert(toBean(noteEntity));
    }

    private NoteBean toBean(NoteEntity noteEntity) {
        return new NoteBean(
                noteEntity.objectId(),
                noteEntity.title(),
                noteEntity.content(),
                noteEntity.status(),
                noteEntity.createdAt(),
                noteEntity.updatedAt());
    }

    @Override
    public boolean isExist(String objectId) {
        return noteBeanDao.count(objectId) > 0;
    }

    @Override
    public Single<List<String>> listObjectId() {
        return noteBeanDao.listObjectId();
    }

}
