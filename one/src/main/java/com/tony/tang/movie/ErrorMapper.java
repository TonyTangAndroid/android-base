package com.tony.tang.movie;

import com.google.gson.Gson;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Response;

class ErrorMapper {

    @Inject
    public ErrorMapper() {
    }

    protected RestApiErrorException toException(Response response) {
        try {
            ResponseErrorWrapper errorWrapper;
            ResponseBody responseBody = response.errorBody();
            final String errorBody = responseBody != null ? responseBody.string() : null;
            errorWrapper = new Gson().fromJson(errorBody, ResponseErrorWrapper.class);
            throw new RestApiErrorException(errorWrapper.getStatus_message(), errorWrapper.getStatus_code());
        } catch (IOException | NullPointerException e) {
            throw new RestApiErrorException(response.message(), response.code());
        }
    }

}
