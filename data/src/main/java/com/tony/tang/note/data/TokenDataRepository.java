package com.tony.tang.note.data;

import com.jordifierro.androidbase.domain.repository.TokenRepository;

import javax.inject.Inject;


public class TokenDataRepository implements TokenRepository {


    private final TokenCache userCache;

    @Inject
    public TokenDataRepository(TokenCache userCache) {
        this.userCache = userCache;
    }

    @Override
    public String sessionToken() {
        return userCache.get();
    }

    @Override
    public void update(String sessionToken) {
        userCache.save(sessionToken);
    }

    @Override
    public void invalidateSession() {
        userCache.remove();
    }
}
