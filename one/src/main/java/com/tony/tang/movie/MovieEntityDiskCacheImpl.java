package com.tony.tang.movie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;

public class MovieEntityDiskCacheImpl {

    private final InMemoryImpl noteInMemory;
    private final MovieEntityCache noteEntityDao;
    private final ThreadExecutor threadExecutor;


    @Inject
    public MovieEntityDiskCacheImpl(InMemoryImpl noteInMemory,
                                    MovieEntityCache noteEntityDao,
                                    ThreadExecutor threadExecutor) {
        this.noteInMemory = noteInMemory;
        this.noteEntityDao = noteEntityDao;
        this.threadExecutor = threadExecutor;
    }

    public List<MovieEntity> get(String keyword) {
        List<MovieEntity> list = noteEntityDao.list(keyword);
        if (list.size() > 0) {
            noteInMemory.put(keyword, list);
        }
        return list;
    }

    public void put(String keyword, List<MovieEntity> list) {
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
        private final List<MovieEntity> noteEntity;
        private final MovieEntityCache noteEntityDao;

        public CacheWriter(List<MovieEntity> list, MovieEntityCache noteEntityDao) {
            this.noteEntity = list;
            this.noteEntityDao = noteEntityDao;
        }

        @Override
        public void run() {
            this.noteEntityDao.insertOrReplace(noteEntity);
        }
    }


}