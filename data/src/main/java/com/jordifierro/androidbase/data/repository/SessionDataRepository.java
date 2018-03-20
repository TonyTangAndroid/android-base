package com.jordifierro.androidbase.data.repository;

import com.google.gson.Gson;
import com.jordifierro.androidbase.domain.cache.UserCache;
import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.repository.SessionRepository;


public class SessionDataRepository implements SessionRepository {


    private final UserCache userCache;
    private final Gson gson;


    public SessionDataRepository(UserCache userCache, Gson gson) {
        this.userCache = userCache;
        this.gson = gson;
    }

    @Override
    public UserEntity getCurrentUser() {
        String jsonString = userCache.get();
        if (jsonString != null) {
            return gson.fromJson(jsonString, UserEntity.class);
        }
        return new UserEntity();
    }

    @Override
    public void setCurrentUser(UserEntity user) {
        userCache.save(gson.toJson(user));
    }

    @Override
    public void invalidateSession() {
        userCache.remove();
    }
}
