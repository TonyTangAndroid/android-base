package com.tony.tang.movie;


import java.util.List;

import javax.annotation.Nonnull;


public interface MovieEntityCache {

    List<MovieEntity> list(String keyword);

    void delete(long id);

    void insertOrReplace(@Nonnull List<MovieEntity> list);

    void insertOrReplace(@Nonnull MovieEntity list);

    boolean isExist(long id);

    boolean isExist(String id);

}