package com.tony.tang.movie.remote;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tony.tang.movie.domain.DomainException;

import java.io.IOException;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Response;

class ErrorMapper {

    @Inject
    public ErrorMapper() {
    }

    protected DomainException toException(Response response) {
        String errorBody = null;
        try {
            ResponseBody responseBody = response.errorBody();
            errorBody = responseBody != null ? responseBody.string() : null;
            ErrorBean errorBean = errorBean(errorBody);
            if (errorBean != null) {
                return throwServerException(errorBean, response.code());
            } else {
                return throwRawException(response, errorBody);
            }
        } catch (IOException | JsonSyntaxException e) {
            return throwRawException(response, errorBody);
        }
    }

    @Nullable
    private ErrorBean errorBean(String errorBody) throws IOException {
        return errorBody == null ? null : ErrorBean.typeAdapter(new Gson()).fromJson(errorBody);
    }

    private DomainException throwServerException(ErrorBean errorBean, int httpCode) {
        String statusMessage = errorBean.message();
        int code = errorBean.code() == 0 ? httpCode : errorBean.code();
        String message = message(errorBean, statusMessage);
        throw new DomainException(code, message);
    }

    private DomainException throwRawException(Response response, String errorBody) {
        throw new DomainException(response.code(), errorBody != null ? errorBody : response.message());
    }

    @Nullable
    private String message(ErrorBean errorWrapper, String statusMessage) {
        List<String> errors = errorWrapper.errors();
        return statusMessage != null ? statusMessage : errors != null ? errors.toString() : null;
    }

}
