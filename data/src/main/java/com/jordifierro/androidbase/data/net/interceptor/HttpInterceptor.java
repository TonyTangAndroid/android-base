package com.jordifierro.androidbase.data.net.interceptor;

import com.jordifierro.androidbase.data.net.RestApi;

import java.io.IOException;
import java.util.Locale;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpInterceptor implements Interceptor {


    public static final String X_PARSE_APPLICATION_ID = "X-Parse-Application-Id";
    public static final String X_PARSE_REST_API_KEY = "X-Parse-REST-API-Key";
    public static final String X_PARSE_REVOCABLE_SESSION = "X-Parse-Revocable-Session";
    public static final String ACCEPT_LANGUAGE = "Accept-Language";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";

    @DebugLog
    @Inject
    public HttpInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader(ACCEPT_LANGUAGE, Locale.getDefault().getLanguage())
                .addHeader(X_PARSE_APPLICATION_ID, RestApi.PARSE_APPLICATION_ID_VALUE)
                .addHeader(X_PARSE_REVOCABLE_SESSION, "1")
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build();
        return chain.proceed(request);
    }

}
