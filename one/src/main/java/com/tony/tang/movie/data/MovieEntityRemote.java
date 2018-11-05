package com.tony.tang.movie.data;

import com.tony.tang.movie.domain.MovieEntity;

import java.util.List;

import io.reactivex.Single;

public interface MovieEntityRemote {

    Single<List<MovieEntity>> list(String keyword);

}
