package com.tony.tang.note.remote;


import com.tony.tang.note.domain.repository.TokenRepository;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.annotations.EverythingIsNonNull;

@EverythingIsNonNull
class AuthInterceptor implements Interceptor {

    private final TokenRepository tokenRepository;

    String PARSE_SESSION_KEY = "X-Parse-Session-Token";

    public AuthInterceptor(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
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