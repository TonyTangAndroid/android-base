package com.tony.tang.note.app;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;

public class NoteDiskCacheImpl {

    private final InMemoryImpl noteInMemory;
    private final NoteEntityCache noteEntityDao;
    private final ThreadExecutor threadExecutor;


    @Inject
    public NoteDiskCacheImpl(InMemoryImpl noteInMemory,
                             NoteEntityCache noteEntityDao,
                             ThreadExecutor threadExecutor) {
        this.noteInMemory = noteInMemory;
        this.noteEntityDao = noteEntityDao;
        this.threadExecutor = threadExecutor;
    }

    public List<NoteEntity> get(String keyword) {
        List<NoteEntity> list = noteEntityDao.list(keyword);
        if (list.size() > 0) {
            noteInMemory.put(keyword, list);
        }
        return list;
    }

    public void put(String keyword, List<NoteEntity> list) {
        noteInMemory.put(keyword, list);
        this.executeAsynchronously(new CacheWriter(list, this.noteEntityDao));

    }

    private void executeAsynchronously(Runnable runnable) {
        this.threadExecutor.execute(runnable);
    }

    public boolean isExist(String keyword) {
        return noteEntityDao.isExist(keyword);
    }

    public Completable delete(long id) {
        //TODO invalidate memory
        return Completable.fromAction(() -> noteEntityDao.delete(id));
    }


    private static class CacheWriter implements Runnable {
        private final List<NoteEntity> noteEntity;
        private final NoteEntityCache noteEntityDao;

        public CacheWriter(List<NoteEntity> list, NoteEntityCache noteEntityDao) {
            this.noteEntity = list;
            this.noteEntityDao = noteEntityDao;
        }

        @Override
        public void run() {
            this.noteEntityDao.insertOrReplace(noteEntity);
        }
    }


}