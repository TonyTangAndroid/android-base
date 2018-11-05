package com.tony.tang.note.app;

import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;

class Validator<T> {

    private Validator() {
    }

    public static <T> Single<T> validate(Response<T> response) {
        Validator<T> validator = new Validator<>();
        return validator.execute(response);
    }


    private Single<T> execute(Response<T> response) {
        if (response.isSuccessful()) {
            T body = response.body();
            assert body != null;
            return Single.just(body);
        } else {
            return Single.error(toException(response));
        }
    }

    protected RestApiErrorException toException(Response response) {
        try {
            ResponseErrorWrapper errorWrapper;
            ResponseBody responseBody = response.errorBody();
            final String errorBody = responseBody != null ? responseBody.string() : null;
            errorWrapper = new Gson().fromJson(errorBody, ResponseErrorWrapper.class);
            throw new RestApiErrorException(errorWrapper.getError(), errorWrapper.getCode());
        } catch (IOException | NullPointerException e) {
            throw new RestApiErrorException(response.message(), response.code());
        }
    }

}
