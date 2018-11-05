package com.tony.tang.note.app;

import java.io.IOException;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class HttpInterceptor implements Interceptor {


    public static final String ACCEPT_LANGUAGE = "Accept-Language";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";


    @Inject
    public HttpInterceptor() {
    }

    @Override
    public Response intercept(@Nonnull Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader(ACCEPT_LANGUAGE, Locale.getDefault().getLanguage())
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build();
        return chain.proceed(request);
    }

}
