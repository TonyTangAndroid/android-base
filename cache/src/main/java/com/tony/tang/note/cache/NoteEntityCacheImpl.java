package com.tony.tang.note.cache;

import com.google.gson.Gson;
import com.tony.tang.note.data.NoteEntityCache;
import com.tony.tang.note.domain.entity.NoteEntity;
import com.tony.tang.note.db.NoteBean;
import com.tony.tang.note.db.NoteBeanDao;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;


public class NoteEntityCacheImpl implements NoteEntityCache {


    private final Gson gson;
    private final NoteBeanDao noteBeanDao;
    private static final long TTL_IN_DISK = 2 * 5 * 1000;

    @Inject
    public NoteEntityCacheImpl(NoteBeanDao noteBeanDao, Gson gson) {
        this.noteBeanDao = noteBeanDao;
        this.gson = gson;
    }

    @Nullable
    @Override
    public NoteEntity find(String objectId) {
        NoteBean noteBean = noteBeanDao.get(objectId);
        return noteBean != null ? toEntity(noteBean) : null;
    }

    private NoteEntity toEntity(NoteBean noteBean) {
        return gson.fromJson(noteBean.content, NoteEntity.class);
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
        return new NoteBean(noteEntity.objectId(), gson.toJson(noteEntity), System.currentTimeMillis());
    }

    @Override
    public boolean isExist(String objectId) {
        return noteBeanDao.get(objectId) != null;
    }

    @Override
    public boolean isExpired(String objectId) {
        NoteBean noteBean = noteBeanDao.get(objectId);
        return noteBean != null && (System.currentTimeMillis() - noteBean.createdAt) > TTL_IN_DISK;
    }
}