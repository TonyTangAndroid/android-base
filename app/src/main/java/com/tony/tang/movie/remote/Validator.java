package com.tony.tang.movie.remote;

import io.reactivex.Single;
import retrofit2.Response;

class Validator<T> {

    private final ErrorMapper errorMapper;

    private Validator(ErrorMapper errorMapper) {
        this.errorMapper = errorMapper;
    }

    public static <T> Single<T> validate(Response<T> response) {
        Validator<T> validator = new Validator<>(new ErrorMapper());
        return validator.execute(response);
    }

    private Single<T> execute(Response<T> response) {
        if (!response.isSuccessful()) {
            return Single.error(errorMapper.toException(response));
        } else {
            T body = response.body();
            assert body != null;
            return Single.just(body);
        }
    }
}
