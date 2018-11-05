package com.tony.tang.note.domain.repository;

import com.tony.tang.note.domain.entity.Movie;

import java.util.List;

import io.reactivex.Single;


public interface MovieRepository {

    Single<List<Movie>> list(String keyword);

}
