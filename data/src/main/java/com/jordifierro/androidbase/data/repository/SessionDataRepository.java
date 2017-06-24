package com.jordifierro.androidbase.data.repository;

import android.content.SharedPreferences;

import com.jordifierro.androidbase.domain.entity.UserEntity;
import com.jordifierro.androidbase.domain.repository.SessionRepository;

import javax.inject.Inject;


public class SessionDataRepository implements SessionRepository {

    private static final String OBJECT_ID = "objectId";
    private static final String EMAIL = "email";
    private static final String AUTH_TOKEN = "auth_token";

    private final SharedPreferences sharedPreferences;


    @Inject
    public SessionDataRepository(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public UserEntity getCurrentUser() {
        if (sharedPreferences.contains(EMAIL) && sharedPreferences.contains(AUTH_TOKEN) && sharedPreferences.contains(OBJECT_ID)) {
            UserEntity user = new UserEntity(sharedPreferences.getString(EMAIL, null));
            user.setSessionToken(sharedPreferences.getString(AUTH_TOKEN, null));
            user.setObjectId(sharedPreferences.getString(OBJECT_ID, null));
            return user;
        }
        return new UserEntity();
    }

    @Override
    public void setCurrentUser(UserEntity user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(OBJECT_ID, user.getObjectId());
        editor.putString(EMAIL, user.getUsername());
        editor.putString(AUTH_TOKEN, user.getSessionToken());
        editor.apply();
    }

    @Override
    public void invalidateSession() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(EMAIL);
        editor.remove(OBJECT_ID);
        editor.remove(AUTH_TOKEN);
        editor.apply();
    }
}
