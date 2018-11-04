package com.tony.tang.note.data;

import com.tony.tang.note.domain.entity.NoteEntity;
import com.tony.tang.note.domain.executor.ThreadExecutor;

import java.util.List;

import javax.inject.Inject;

/**
 * Interface that represents a data store from where data is retrieved.
 */

public class NoteDiskCacheImpl {

    private final NoteInMemoryImpl noteInMemory;
    private final NoteEntityCache noteEntityDao;
    private final ThreadExecutor threadExecutor;


    @Inject
    public NoteDiskCacheImpl(NoteInMemoryImpl noteInMemory,
                             NoteEntityCache noteEntityDao,
                             ThreadExecutor threadExecutor) {
        this.noteInMemory = noteInMemory;
        this.noteEntityDao = noteEntityDao;
        this.threadExecutor = threadExecutor;
    }

    public NoteEntity get(String objectId) {
        NoteEntity noteEntity = noteEntityDao.find(objectId);
        if (noteEntity != null) {
            noteInMemory.put(noteEntity);
        }
        return noteEntity;
    }

    public void put(NoteEntity noteEntity) {
        noteInMemory.put(noteEntity);
        this.executeAsynchronously(new CacheWriter(noteEntity, this.noteEntityDao));


    }

    public void delete(String objectId) {
        noteInMemory.evict(objectId);
        noteEntityDao.delete(objectId);
    }

    public boolean isValid(String objectId) {
        return noteEntityDao.isExist(objectId) && !noteEntityDao.isExpired(objectId);
    }

    public void put(List<NoteEntity> list) {
        for (NoteEntity noteEntity : list) {
            put(noteEntity);
        }
    }

    private void executeAsynchronously(Runnable runnable) {
        this.threadExecutor.execute(runnable);
    }


    private static class CacheWriter implements Runnable {
        private final NoteEntity noteEntity;
        private final NoteEntityCache noteEntityDao;

        public CacheWriter(NoteEntity noteEntity, NoteEntityCache noteEntityDao) {
            this.noteEntity = noteEntity;
            this.noteEntityDao = noteEntityDao;
        }

        @Override
        public void run() {
            this.noteEntityDao.insertOrReplace(noteEntity);
        }
    }


}