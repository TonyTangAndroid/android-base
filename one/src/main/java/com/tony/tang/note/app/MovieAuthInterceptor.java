package com.tony.tang.note.app;


import java.io.IOException;

import javax.annotation.Nonnull;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class MovieAuthInterceptor implements Interceptor {

    private final static String QUERY_KEY = "api_key";
    private final String apiKey;

    public MovieAuthInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Response intercept(@Nonnull Chain chain) throws IOException {
        HttpUrl url = chain.request().url();
        HttpUrl.Builder builder = url.newBuilder();
        builder.addQueryParameter(QUERY_KEY, apiKey);
        Request request = chain.request().newBuilder().url(builder.build()).build();
        return chain.proceed(request);
    }
}