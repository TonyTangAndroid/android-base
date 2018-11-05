package com.tony.tang.note.remote;

import com.tony.tang.note.domain.entity.Movie;
import com.tony.tang.note.domain.entity.MoviesWrapper;
import com.tony.tang.note.domain.repository.MovieRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class MovieRemoteRepository implements MovieRepository {

    private final MovieRestApi restApi;

    @Inject
    public MovieRemoteRepository(MovieRestApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public Single<List<Movie>> list(String keyword) {
        return this.restApi.searchRx(keyword)
                .flatMap(Validator::validate)
                .map(MoviesWrapper::getResults);
    }
}
