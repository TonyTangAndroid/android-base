package com.jordifierro.androidbase.data.repository;

import android.content.SharedPreferences;

import com.jordifierro.androidbase.domain.cache.UserCache;


public class UserCacheImpl implements UserCache {

    private static final String PREF_USER_KEY = "pref_user_key";

    private final SharedPreferences sharedPreferences;


    public UserCacheImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void save(String userJsonString) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_USER_KEY, userJsonString);
        editor.apply();
    }

    @Override
    public void remove() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(PREF_USER_KEY);
        editor.apply();
    }

    @Override
    public String get() {

        return sharedPreferences.getString(PREF_USER_KEY, null);
    }
}
