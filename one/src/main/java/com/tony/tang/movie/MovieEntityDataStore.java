package com.tony.tang.movie;

import java.util.List;

import io.reactivex.Single;

public interface MovieEntityDataStore {
    Single<List<MovieEntity>> list(String keyword);
}