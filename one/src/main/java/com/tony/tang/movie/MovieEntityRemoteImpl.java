package com.tony.tang.movie;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class MovieEntityRemoteImpl implements MovieEntityRemote {

    private final RestApi restApi;

    @Inject
    public MovieEntityRemoteImpl(RestApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public Single<List<MovieEntity>> list(String keyword) {
        Single<MovieEntityDto> moviesWrapperSingle = this.restApi.searchRx(keyword)
                .flatMap(Validator::validate);
        return moviesWrapperSingle.map(MovieEntityDto::getResults);
    }
}
