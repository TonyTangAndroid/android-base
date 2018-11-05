package com.tony.tang.movie.domain;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;


public interface MovieEntityRepository {

    Single<List<MovieEntity>> list(String keyword);

    Completable delete(long id);
}
