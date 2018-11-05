package com.tony.tang.note.app;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class NoteRemoteImpl implements NoteRemote {

    private final RestApi restApi;

    @Inject
    public NoteRemoteImpl(RestApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public Single<List<NoteEntity>> list(String keyword) {
        Single<MoviesWrapper> moviesWrapperSingle = this.restApi.searchRx(keyword)
                .flatMap(Validator::validate);
        return moviesWrapperSingle.map(MoviesWrapper::getResults);
    }
}
