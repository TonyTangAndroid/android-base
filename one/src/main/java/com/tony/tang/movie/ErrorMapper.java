package com.tony.tang.movie;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Response;

class ErrorMapper {

    @Inject
    public ErrorMapper() {
    }

    protected RestApiErrorException toException(Response response) {
        String errorBody = null;
        try {
            ResponseBody responseBody = response.errorBody();
            errorBody = responseBody != null ? responseBody.string() : null;
            ResponseErrorWrapper errorWrapper = new Gson().fromJson(errorBody, ResponseErrorWrapper.class);
            String statusMessage = errorWrapper.getStatus_message();
            int code = code(response, errorWrapper);
            String message = message(errorWrapper, statusMessage);
            throw new RestApiErrorException(message, code);
        } catch (IOException | JsonSyntaxException e) {
            throw new RestApiErrorException(errorBody != null ? errorBody : response.message(), response.code());
        }
    }

    private int code(Response response, ResponseErrorWrapper errorWrapper) {
        return errorWrapper.getStatus_code() == 0 ? response.code() : errorWrapper.getStatus_code();
    }

    private String message(ResponseErrorWrapper errorWrapper, String statusMessage) {
        return statusMessage == null ? errorWrapper.getErrors().toString() : statusMessage;
    }

}
