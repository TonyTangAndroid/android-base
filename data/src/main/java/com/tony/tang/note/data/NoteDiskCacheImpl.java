package com.tony.tang.note.data;

import com.tony.tang.note.domain.entity.NoteEntity;
import com.tony.tang.note.domain.executor.ThreadExecutor;

import java.util.List;

import javax.inject.Inject;

public class NoteDiskCacheImpl {

    private final NoteEntityCache noteEntityDao;
    private final ThreadExecutor threadExecutor;


    @Inject
    public NoteDiskCacheImpl(NoteEntityCache noteEntityDao,
                             ThreadExecutor threadExecutor) {
        this.noteEntityDao = noteEntityDao;
        this.threadExecutor = threadExecutor;
    }


    public void put(NoteEntity noteEntity) {
        this.executeAsynchronously(new CacheWriter(noteEntity, this.noteEntityDao));
    }

    public void delete(String objectId) {
        noteEntityDao.delete(objectId);
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