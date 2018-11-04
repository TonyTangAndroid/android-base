package com.jordifierro.androidbase.data.repository;

import com.jordifierro.androidbase.domain.cache.UserCache;
import com.jordifierro.androidbase.domain.repository.TokenRepository;

import javax.inject.Inject;


public class TokenDataRepository implements TokenRepository {


    private final UserCache userCache;

    @Inject
    public TokenDataRepository(UserCache userCache) {
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
