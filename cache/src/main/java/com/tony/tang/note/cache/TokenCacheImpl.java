package com.tony.tang.note.cache;

import android.content.SharedPreferences;

import com.tony.tang.note.data.TokenCache;

import javax.inject.Inject;


public class TokenCacheImpl implements TokenCache {

    private static final String PREF_USER_KEY = "pref_user_key";

    private final SharedPreferences sharedPreferences;

    @Inject
    public TokenCacheImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void save(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_USER_KEY, token);
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
