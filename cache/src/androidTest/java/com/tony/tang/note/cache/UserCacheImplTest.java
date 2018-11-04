package com.tony.tang.note.cache;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class UserCacheImplTest {

    private static final String SHARED_PACKAGE = "SharedPreferencesTest";
    private static final String FAKE_JSON = "test@email.com";

    private TokenCacheImpl tokenCache;


    @Before
    public void setup() {

        Context context = ApplicationProvider.getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PACKAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        this.tokenCache = new TokenCacheImpl(sharedPreferences);
    }

    @Test
    public void save() {
        this.tokenCache.save(FAKE_JSON);
        assertThat(this.tokenCache.get()).isEqualTo(FAKE_JSON);
    }

    @Test
    public void remove() {
        save();
        this.tokenCache.remove();
        assertThat(this.tokenCache.get()).isNull();
    }
}
