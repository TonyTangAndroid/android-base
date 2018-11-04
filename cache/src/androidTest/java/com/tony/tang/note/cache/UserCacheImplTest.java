package com.tony.tang.note.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.tony.tang.note.data.TokenCache;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class UserCacheImplTest {

    private static final String SHARED_PACKAGE = "SharedPreferencesTest";
    private static final String FAKE_JSON = "test@email.com";

    private TokenCache tokenCache;

    @Test
    public void testGetWithoutSetReturnsNull() {

        Context targetContext = ApplicationProvider.getApplicationContext();
        SharedPreferences sharedPreferences = targetContext
                .getSharedPreferences(SHARED_PACKAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        this.tokenCache = new TokenCacheImpl(sharedPreferences);
        assertThat(this.tokenCache.get()).isNull();
    }

    public void testSetAndGetUser() {

        Context targetContext = ApplicationProvider.getApplicationContext();
        SharedPreferences sharedPreferences = targetContext
                .getSharedPreferences(SHARED_PACKAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        this.tokenCache = new TokenCacheImpl(sharedPreferences);
        assertThat(this.tokenCache.get()).isNull();

        this.tokenCache.save(FAKE_JSON);
        String actual = this.tokenCache.get();
        assertThat(actual).isEqualTo(FAKE_JSON);
    }

    public void testSetInvalidateAndGetNullUser() {
        testSetAndGetUser();
        this.tokenCache.remove();
        assertThat(this.tokenCache.get()).isNull();
    }
}
