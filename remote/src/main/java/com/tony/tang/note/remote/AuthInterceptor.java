package com.tony.tang.note.remote;


import com.jordifierro.androidbase.domain.repository.TokenRepository;

import java.io.IOException;

import javax.annotation.Nonnull;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class AuthInterceptor implements Interceptor {

    private final TokenRepository tokenRepository;

    String PARSE_SESSION_KEY = "X-Parse-Session-Token";

    public AuthInterceptor(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Response intercept(@Nonnull Chain chain) throws IOException {
        String sessionToken = tokenRepository.sessionToken();
        if (sessionToken == null) {
            return chain.proceed(chain.request());
        } else {
            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader(PARSE_SESSION_KEY, sessionToken);
            return chain.proceed(builder.build());
        }
    }
}