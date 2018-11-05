package com.tony.tang.movie.cache;

import com.tony.tang.movie.data.MovieEntityCache;
import com.tony.tang.movie.db.MovieEntityDao;
import com.tony.tang.movie.domain.MovieEntity;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;


class MovieEntityCacheImpl implements MovieEntityCache {


    private final MovieEntityDao noteBeanDao;

    @Inject
    public MovieEntityCacheImpl(MovieEntityDao noteBeanDao) {
        this.noteBeanDao = noteBeanDao;
    }

    @Override
    public List<MovieEntity> list(String keyword) {
        return noteBeanDao.list(keyword);
    }

    @Override
    public void delete(long id) {
        noteBeanDao.delete(id);
    }

    @Override
    public void insertOrReplace(@Nonnull List<MovieEntity> list) {
        noteBeanDao.insert(list);
    }

    @Override
    public void insertOrReplace(@Nonnull MovieEntity noteEntity) {
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
