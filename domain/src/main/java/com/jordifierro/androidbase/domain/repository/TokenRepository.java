package com.jordifierro.androidbase.domain.repository;

import javax.annotation.Nullable;

public interface TokenRepository {
    @Nullable
    String sessionToken();

    void update(String sessionToken);

    void invalidateSession();
}
