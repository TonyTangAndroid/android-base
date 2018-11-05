package com.tony.tang.note.app;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;


public class NoteEntityCacheImpl implements NoteEntityCache {


    private final NoteEntityDao noteBeanDao;

    @Inject
    public NoteEntityCacheImpl(NoteEntityDao noteBeanDao) {
        this.noteBeanDao = noteBeanDao;
    }

    @Override
    public List<NoteEntity> list(String keyword) {
        return noteBeanDao.list(keyword);
    }

    @Override
    public void delete(long id) {
        noteBeanDao.delete(id);
    }

    @Override
    public void insertOrReplace(@Nonnull List<NoteEntity> list) {
        noteBeanDao.insert(list);
    }

    @Override
    public void insertOrReplace(@Nonnull NoteEntity noteEntity) {
        noteBeanDao.insert(noteEntity);
    }

    @Override
    public boolean isExist(long id) {
        return noteBeanDao.get(id) != null;
    }

    @Override
    public boolean isExist(String keyword) {
        return noteBeanDao.count(keyword) > 0;
    }

}
